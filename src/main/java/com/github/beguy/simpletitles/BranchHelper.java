package com.github.beguy.simpletitles;


import javax.swing.JFrame;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.FrameTitleBuilder;


/**
 * @author vi
 */
public class BranchHelper
{

    private final Logger logger = Logger.getInstance("FrameHelper");

    private final Project project;

    private String currentBranchName;


    public BranchHelper(Project project)
    {
        this.project = project;
    }


    public String getCurrentBranchName()
    {
        return currentBranchName;
    }


    public void onBranchChanged(String branchName)
    {
        currentBranchName = branchName;
        updateFrameTitle();
    }


    private void updateFrameTitle()
    {
        final String projectTitle = ServiceManager.getService(FrameTitleBuilder.class)
                .getProjectTitle(project);
        JFrame ideFrame = WindowManager.getInstance().getFrame(project);
        if (ideFrame != null)
        {
            ideFrame.setTitle(projectTitle);
        }
        else
        {
            logger.info("unable to obtain IdeFrame (WindowManager returned null ideFrame)");
        }
    }
}
