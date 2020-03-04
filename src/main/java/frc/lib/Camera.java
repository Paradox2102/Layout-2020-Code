package frc.lib;

import java.util.ArrayList;
import java.util.List;

import frc.PiCamera.PiCamera;
import frc.PiCamera.PiCamera.PiCameraRect;
import frc.PiCamera.PiCamera.PiCameraRegion;
import frc.PiCamera.PiCamera.PiCameraRegions;

public class Camera {
    public PiCamera m_piCamera;

    public void connect(String ipAddress) {
        m_piCamera = new PiCamera();

        m_piCamera.Connect(ipAddress, 5800);
    }

    public void DumpFrames(int count) {
        m_piCamera.DumpFrames(count);
    }

    public void setImageProcessing(boolean set) {
    }

    public void StartPiLog() {
        m_piCamera.StartPiLog();
    }

    public void EndPiLog() {
        m_piCamera.EndPiLog();
    }

    public static enum BallSide {
        RIGHT, LEFT
    };

    public class CameraData {
        public PiCameraRegions m_regions;

        public CameraData() {
            m_regions = m_piCamera.GetRegions();
        }

        public void DumpFrames(int count) {
            m_piCamera.DumpFrames(count);
        }

        public boolean canSee() {
            if (m_regions != null) {
                return m_regions.GetRegion(0) != null;
            }
            return false;
        }

        public boolean canSeeMulti(int num) {
            if (m_regions != null) {
                for (int i = 0; i < num; i++) {
                    if (m_regions.GetRegion(i) == null) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        public double centerX() {
            PiCameraRegion region = m_regions.GetRegion(0);
            PiCameraRect bounds = region.m_bounds;
            return (bounds.m_right + bounds.m_left) / 2.0;
        }
        
        public double centerX(PiCameraRegion region) {
            PiCameraRect bounds = region.m_bounds;
            return (bounds.m_right + bounds.m_left) / 2.0;
        }

        public double centerLine() {
            return m_regions.m_targetHorzPos;
        }

        public double centerDiff(double center, double offset) {
            double targetCenter = centerX();
            return center - targetCenter - offset;
        }

        public double centerDiff(double center, double offset, PiCameraRegion region) {
            double targetCenter = centerX(region);
            return center - targetCenter - offset;
        }

        public double ballCenterDiff(double centerLine) {
            PiCameraRegions regions = m_regions;
            regions.m_regions = ballFilter();
            PiCameraRegion region1 = m_regions.GetRegion(0);
            PiCameraRegion region2 = m_regions.GetRegion(1);
            // left case
            if (region1.m_bounds.m_left < region2.m_bounds.m_left) {
                double center = (region1.m_bounds.m_left + region2.m_bounds.m_right) / 2.0;
                return centerLine - center;
            } else {
                double center = (region2.m_bounds.m_left + region1.m_bounds.m_right) / 2.0;
                return centerLine - center;
            }
        }

        public double ballCenterDiff(double centerLine, ArrayList<PiCameraRegion> regions) {
            PiCameraRegion region1 = m_regions.GetRegion(0);
            PiCameraRegion region2 = m_regions.GetRegion(1);
            // left case
            if (region1.m_bounds.m_left < region2.m_bounds.m_left) {
                double center = (region1.m_bounds.m_left + region2.m_bounds.m_right) / 2.0;
                return centerLine - center;
            } else {
                double center = (region2.m_bounds.m_left + region1.m_bounds.m_right) / 2.0;
                return centerLine - center;
            }
        }

        public List<PiCameraRegion> sortRegions() {
            List<PiCameraRegion> regions = m_regions.m_regions;
            regions = ballFilter();
            if (regions.size() > 1) {
                List<PiCameraRegion> sortedTargets = new ArrayList<PiCameraRegion>();
                sortedTargets.add(regions.get(0));

                for (int i = 1; i < regions.size(); i++) {
                    int idx = sortedTargets.size();
                    for (int j = sortedTargets.size() - 1; j > -1; j--) {
                        if (regions.get(i).m_bounds.m_left < sortedTargets.get(j).m_bounds.m_left) {
                            idx = j;
                        }
                    }
                    sortedTargets.add(idx, regions.get(i));
                }
                for(PiCameraRegion region : sortedTargets){
                    System.out.println(region.m_bounds.m_left);
                }
                return sortedTargets;
            } else {
                return null;
            }
        }

        public ArrayList<PiCameraRegion> ballSelector(BallSide side) {
            List<PiCameraRegion> regions = sortRegions();
            ArrayList<PiCameraRegion> returnRegions = new ArrayList<PiCameraRegion>();

            if (side.equals(BallSide.RIGHT)) {
                returnRegions.add(regions.get(regions.size() - 1));
                returnRegions.add(regions.get(regions.size() - 2));
            } else if (side.equals(BallSide.LEFT)) {
                System.out.println(regions.get(0).m_bounds.m_left);
                System.out.println(regions.get(1).m_bounds.m_left);

                returnRegions.add(regions.get(0));
                returnRegions.add(regions.get(1));

            }

            return returnRegions;
        }

        public ArrayList<PiCameraRegion> ballFilter() {
            ArrayList<PiCameraRegion> regionsList = new ArrayList<PiCameraRegion>();
            if (m_regions != null) {
                for (int i = 0; i < m_regions.GetRegionCount(); i++) {
                    PiCameraRect rect = m_regions.GetRegion(i).m_bounds;

                    double height = rect.m_bottom - rect.m_top;
                    double width = rect.m_right - rect.m_left;

                    if (width / height > 0.7 && width / height < 1.3) {
                        regionsList.add(m_regions.GetRegion(i));
                    }
                }
            }
            return regionsList;
        }

        public double getTargetHeight() {
            if (m_regions.GetRegionCount() > 0) {
                return m_regions.GetRegion(0).m_bounds.m_bottom - m_regions.GetRegion(0).m_bounds.m_top;
            } else {
                return -1;
            }
        }

        public double getTargetWidth() {
            if (m_regions.GetRegionCount() > 0) {
                return m_regions.GetRegion(0).m_bounds.m_right - m_regions.GetRegion(0).m_bounds.m_left;
            } else {
                return -1;
            }
        }

    }

    public CameraData createData() {
        return new CameraData();
    }
    
    boolean m_lightsState = false;
    public void toggleLights(boolean on) {
        m_lightsState = on;
        m_piCamera.SetLight(on ? 3 : 0);
    }

    public boolean getLightsState(){
        return m_lightsState;
    }

}