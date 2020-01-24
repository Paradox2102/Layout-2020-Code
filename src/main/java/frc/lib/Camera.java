package frc.lib;

import frc.PiCamera.PiCamera;
import frc.PiCamera.PiCamera.PiCameraRect;
import frc.PiCamera.PiCamera.PiCameraRegion;
import frc.PiCamera.PiCamera.PiCameraRegions;

public class Camera {
    public static PiCamera m_piCamera;

    public void connect(String ipAddress){
        m_piCamera = new PiCamera();

        m_piCamera.Connect(ipAddress, 5800);
    }

    public class CameraData{
        PiCameraRegions m_regions;

        public CameraData(){
            m_regions = m_piCamera.GetRegions();
        }

        public boolean canSee(){
            return m_regions.GetRegion(0) != null;
        }

        public double centerX(){
            PiCameraRegion region = m_regions.GetRegion(0);
            PiCameraRect bounds = region.m_bounds;
            return (bounds.m_right + bounds.m_left )/ 2.0;
        }

        public double centerLine(){
            return m_regions.m_targetHorzPos;
        }

        public double centerDiff(double center){
            double targetCenter = centerX();
            return center - targetCenter;
        }

    }

    public CameraData createData(){
        return new CameraData();
    }

    public void toggleLights(boolean on){

        m_piCamera.SetLight(on ? 3 : 0);
    }

}