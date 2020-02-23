package frc.lib;

import java.util.ArrayList;

import frc.PiCamera.PiCamera;
import frc.PiCamera.PiCamera.PiCameraRect;
import frc.PiCamera.PiCamera.PiCameraRegion;
import frc.PiCamera.PiCamera.PiCameraRegions;

public class Camera {
    public static PiCamera m_piCamera;

    public void connect(String ipAddress) {
        m_piCamera = new PiCamera();

        m_piCamera.Connect(ipAddress, 5800);
    }

    public void DumpFrames(int count) {
        m_piCamera.DumpFrames(count);
    }

    public void StartPiLog() {
        m_piCamera.StartPiLog();
    }

    public void EndPiLog() {
        m_piCamera.EndPiLog();
    }

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

        public double centerX() {
            PiCameraRegion region = m_regions.GetRegion(0);
            PiCameraRect bounds = region.m_bounds;
            return (bounds.m_right + bounds.m_left) / 2.0;
        }

        public double centerLine() {
            return m_regions.m_targetHorzPos;
        }

        public double centerDiff(double center) {
            double targetCenter = centerX();
            return center - targetCenter;
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

        public ArrayList<PiCameraRegion> ballFilter() {
            ArrayList<PiCameraRegion> regionsList = new ArrayList<PiCameraRegion>();
            for (int i = 0; i < m_regions.GetRegionCount(); i++) {
                PiCameraRect rect = m_regions.GetRegion(i).m_bounds;

                double height = rect.m_bottom - rect.m_top;
                double width = rect.m_right - rect.m_left;

                if (width / height > 0.6 && width / height < 1.4) {
                    regionsList.add(m_regions.GetRegion(i));
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

    public void toggleLights(boolean on) {

        m_piCamera.SetLight(on ? 3 : 0);
    }

}