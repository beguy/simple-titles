package com.github.beguy.simpletitles;


import java.nio.file.Paths;
import java.util.Optional;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.PlatformFrameTitleBuilder;
import org.jetbrains.annotations.NotNull;


public class SimpleFrameTitleBuilder extends PlatformFrameTitleBuilder
{

    @Override public String getProjectTitle(@NotNull Project project)
    {
        String currentBranch = Optional.ofNullable(ServiceManager.getService(project, BranchHelper.class))
                .map(BranchHelper::getCurrentBranchName)
                // return empty string if branch name is unavailable
                .orElse("");
        String currentBranchWithBraces = currentBranch.isEmpty() ? "" : "[" + currentBranch + "]";

        return Paths.get(project.getBasePath()).getFileName().toString() + currentBranchWithBraces;
    }


    @Override
    public String getFileTitle(@NotNull Project project, @NotNull VirtualFile virtualFile)
    {
        return virtualFile.getNameWithoutExtension();
    }
}