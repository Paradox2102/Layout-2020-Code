#!/usr/bin/env python

import re;
from collections import defaultdict
import io
import subprocess

FILE = "../src/main/java/frc/robot/RobotContainer.java"

def process(java):
    # Get rid of comments
    java = re.sub(r'//.*?\n', '\n', java)
    java = re.sub(r'/\*.*?\*/', '', java, re.DOTALL)
    joysticks = {}
    for m in re.finditer(r'\bJoystick\s+(\w+)\s*=\s*new\s+Joystick\((\d+)\);', java, re.DOTALL):
        (stick, port) = m.group(1,2)
        port = int(port)
        joysticks[stick] = dict(name=stick, port=port, buttons=defaultdict(list))
        #print("Joystick", stick, port)
    buttons = {}
    for m in re.finditer(r'\bJoystickButton\s+(\w+)\s*=\s*new\s+JoystickButton\((\w+),\s*(\d+)\);', java, re.DOTALL):
        (button, stick, num) = m.group(1,2,3)
        num = int(num)
        d = dict(name=button, stick=stick, num=num, actions=[])
        joysticks[stick]['buttons'][num].append(d)
        buttons[button] = d
        #print("Button", button, stick, num)
    for m in re.finditer(r'\b(\w+)\.(toggleWhenPressed|cancelWhenPressed|whenHeld|whenPressed|whenReleased|whileHeld)\((.*?)\);', java, re.DOTALL):
        (button, method, command) = m.group(1,2,3)
        command = command.strip()
        command = re.sub(r'\s+', ' ', command)
        d = dict(button=button, method=method, command=command)
        buttons[button]['actions'].append(d)
        #print("ButtonAction", button, method, command)
    return joysticks

    
def clean_name(name):
    if name.startswith("m_"):
        return name[2:]
    else:
        return name
    

def wrap_code(java):
    return '```' + java + '```'
    

def get_file_last_commit(file):
    out = subprocess.Popen(['git', 'log', '-n', '1', file],
           stdout=subprocess.PIPE, 
           stderr=subprocess.STDOUT)
    stdout,stderr = out.communicate()
    result = stdout.decode()
    result = re.sub(r'\n\s*', " ", result)
    return result


def md_joysticks(data):
    output = io.StringIO()
    joysticks = sorted(data.values(), key=lambda d: d['port'])
    print("# Joystick layout", file=output)
    print("\n<img align=\"right\" src=\"Joystick.png\">", file=output)
    for j in joysticks:
        print("\n## Joystick %d: %s" % (j['port'], clean_name(j['name'])), file=output)
        buttonlists = sorted(j['buttons'].items())
        for num, buttons in buttonlists:
            if len(buttons) > 1:
                print("\n* __Button %d__:" % (num), file=output)
                for b in buttons:
                    if len(b['actions']) > 1:
                        print("\n  * %s:" % clean_name(b['name']), file=output)
                        for a in b['actions']:
                            print("\n    * _%s_: %s" % (a['method'], wrap_code(a['command'])), file=output)
                    elif len(b['actions']) == 1:     
                        a = b['actions'][0]
                        print("\n  * %s: _%s_: %s" % (clean_name(b['name']), a['method'], wrap_code(a['command'])), file=output)
                    else:
                    	print("\n  * %s" % (clean_name(b['name'])), file=output)
            else:
                b = buttons[0]
                if len(b['actions']) > 1:
                    print("\n* __Button %d__: %s:" % (num, clean_name(b['name'])), file=output)
                    for a in b['actions']:
                        print("\n  * _%s_: %s" % (a['method'], wrap_code(a['command'])), file=output)
                elif len(b['actions']) == 1:     
                    a = b['actions'][0]
                    print("\n* __Button %d__: %s: _%s_: %s" % (num, clean_name(b['name']), a['method'], wrap_code(a['command'])), file=output)
                else:     
                    print("\n* __Button %d__: %s:" % (num, clean_name(b['name'])), file=output)
    last_commit = get_file_last_commit(FILE)
    print("\n```" + last_commit + "```", file=output)
                    
    return output.getvalue()
    
    
def main():
	with open(FILE, "r") as f:
		java = f.read()
	#print(java)
	data = process(java)
	with open("joystick.md", "w") as f:
		print(md_joysticks(data), file=f)
	
		
main()
