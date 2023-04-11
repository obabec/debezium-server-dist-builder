package io.debezium.server.dist.builder;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

public class ProjectBuilder {
    private static final String GIT_REPO = "https://github.com/debezium/debezium-server.git";
    private final String targetPath;
    private String pathToTargetPom;
    private Git distRepo;

    public ProjectBuilder(String targetPath) {
        this.targetPath = targetPath;
        try {
            this.distRepo = Git.cloneRepository().setDepth(1).setURI(GIT_REPO).setDirectory(new File(this.targetPath)).setNoCheckout(true).call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public ProjectBuilder cloneDistFolder() {
        try {
            String serverFolder = "debezium-server-dist";
            this.distRepo.checkout().setName("main").setStartPoint("origin/main").addPath(serverFolder).call();
            this.distRepo.getRepository().close();
            this.pathToTargetPom = String.format("%s/%s/pom.xml", targetPath, serverFolder);
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ProjectBuilder removeCurrentPom() {
        File currentPom = new File(pathToTargetPom);
        currentPom.delete();
        return this;
    }

    public void buildProject(DebeziumServerPomBuilder pomBuilder, boolean mavenBuild) {
        pomBuilder.write(pathToTargetPom);
        if (mavenBuild) {
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile( new File( pathToTargetPom ) );
            request.setGoals(Collections.singletonList("package"));
            request.setProfiles(Collections.singletonList("assembly"));
            Properties props = new Properties();
            props.put("maven.test.skip", "");
            request.setProperties(props);
            Invoker invoker = new DefaultInvoker();
            invoker.setMavenHome(new File("/usr/local/Cellar/maven/3.9.1/libexec"));
            try {
                invoker.execute( request );
            } catch (MavenInvocationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
