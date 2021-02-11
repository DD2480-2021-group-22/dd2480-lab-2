import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

/**
 * The RepoSnapshot class represents a GitHub repository at a certain commit.
 */
public class RepoSnapshot {

    private String name;
    private String url;
    private String commitHash;

    /**
     * Construct a RepoSnapshot object from the given parameters.
     * @param name the name of the repo
     * @param url the url of the repo
     * @param commitHash the commit hash
     */
    public RepoSnapshot(String name, String url, String commitHash) {
        this.name = name;
        this.url = url;
        this.commitHash = commitHash;
    }

    /**
     * Constructs a repository from a webhook payload.
     * @param payload The payload from which to retrieve repository information.
     */
    public RepoSnapshot(Payload payload) {
        this.name = payload.getRepoName();
        this.url = payload.getUrl();
        this.commitHash = payload.getCommitHash();
    }

    /**
     * Clones the repository to the target directory, and then checks it out
     * at the commit corresponding to this RepoSnapshot object.
     *
     * @param targetDirectory the directory in which to place the cloned repository.
     * @return The path to the cloned files.
     * @throws GitAPIException if the clone command fails.
     */
    public File cloneFiles(File targetDirectory) throws GitAPIException {

        String dirName = String.format("%s-%s", name, commitHash);
        File repoDir = new File(targetDirectory, dirName);

        CloneCommand clone = Git.cloneRepository();
        clone.setDirectory(repoDir);
        clone.setURI(url);
        Git git = clone.call();

        try {
            CheckoutCommand checkout = git.checkout();
            checkout.setName(commitHash);
            checkout.setCreateBranch(false);
            checkout.call();
        } finally {
            git.close();
        }

        return repoDir;
    }
}
