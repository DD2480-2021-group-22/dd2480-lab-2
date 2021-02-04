import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the RepoSnapshot class.
 */
public class RepoSnapshotTest {

    /**
     * Asserts that the lab repo is cloned correctly into a temporary folder by
     * checking that the repo cloned is non-empty and that the git ref matches the given commit.
     * @param tempDir A temporary directory provided by JUnit.
     * @throws GitAPIException if test fails
     * @throws IOException if test fails
     */
    @Test
    public void realRepoClonedCorrectly(@TempDir Path tempDir) throws GitAPIException, IOException {
        // Arrange
        String repoName = "dd2480-lab-2"; // The name of our repo
        String repoUrl = "https://github.com/grundb/dd2480-lab-2"; // The repo URL
        String commitHash = "750eb142f8746ca9bb52b6d21156afbea13ad80f"; // The first commit
        RepoSnapshot rs = new RepoSnapshot(repoName, repoUrl, commitHash);

        // Act
        File clonedDir = rs.cloneFiles(tempDir.toFile()); // will throw on failure
        String headHash = Git.open(clonedDir).getRepository().findRef("HEAD").getObjectId().getName();
        int nrFiles = clonedDir.listFiles().length;

        // Assert
        assertEquals(headHash, commitHash);
        assertTrue(nrFiles > 0); // repo should be non-empty
    }

    /**
     * Asserts that attempting to clone a non-existent repo into a temporary
     * folder throws an exception.
     * @param tempDir temporary directory provided by JUnit.
     */
    @Test
    public void fakeRepoNotClonedCorrectly(@TempDir Path tempDir) {
        // Arrange
        String repoName = "dd2480-lab-2"; // The name of our repo
        String repoUrl = "https://github.com/grundb/not-a-real-repo-auhsgjkashashljsh"; // The incorrect repo url
        String commit = "750eb142f8746ca9bb52b6d21156afbea13ad80f"; // The first commit in our lab repo
        RepoSnapshot rs = new RepoSnapshot(repoName, repoUrl, commit);

        // Act, assert
        assertThrows(GitAPIException.class, () -> {
            rs.cloneFiles(tempDir.toFile());
        });
    }

    /**
     * Asserts that attempting to clone a repo at a non-existent commit
     * throws an exception.
     * @param tempDir temporary directory provided by JUnit.
     */
    @Test
    public void fakeCommitNotClonedCorrectly(@TempDir Path tempDir) {
        // Arrange
        String repoName = "dd2480-lab-2"; // The name of our repo
        String repoUrl = "https://github.com/grundb/dd2480-lab-2"; // The repo URL
        String commit = "abc123abc123abc123abc123abc123abc123abc1"; // Probably not a commit
        RepoSnapshot rs = new RepoSnapshot(repoName, repoUrl, commit);

        // Act, assert
        assertThrows(Exception.class, () -> {
            rs.cloneFiles(tempDir.toFile());
        });
    }
}
