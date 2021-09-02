package com.github.beguy.simpletitles;


import java.util.Optional;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import git4idea.GitLocalBranch;
import git4idea.GitReference;
import git4idea.branch.GitBranchUtil;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;


public class GitListener implements StartupActivity
{
    @Override
    public void runActivity(@NotNull Project project)
    {
        project.getMessageBus().connect().subscribe(GitRepository.GIT_REPO_CHANGE, repository -> {
            GitLocalBranch currentBranch = repository.getCurrentBranch();
            if (currentBranch != null)
            {
                notifyBranchChanged(repository.getProject(), currentBranch.getName());
            }
        });
        Optional.ofNullable(GitBranchUtil.getCurrentRepository(project))
                .map(GitRepository::getCurrentBranch)
                .map(GitReference::getName)
                .ifPresent(branchName -> {
                    notifyBranchChanged(project, branchName);
                });
    }


    private void notifyBranchChanged(Project project, String branchName)
    {
        final BranchHelper service = ServiceManager.getService(project, BranchHelper.class);
        if (service == null)
        {
            return;
        }
        service.onBranchChanged(branchName);
    }
}