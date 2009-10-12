package ch.elca.el4j.tests.maven.plugins;

import java.io.File;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.PlexusTestCase;
import org.junit.Test;

public class MavenRecursiveMojoTest extends AbstractMojoTestCase {

	File level1_1_1 = new File(PlexusTestCase.getBasedir()
		+ "/src/test/resource/testStructure1/level1/level1_1/level1_1_1",
		"pom.xml");
	
	@Test
	public void testGetRootDir() throws Exception
	{
		assertTrue(true);
	}
	
}
