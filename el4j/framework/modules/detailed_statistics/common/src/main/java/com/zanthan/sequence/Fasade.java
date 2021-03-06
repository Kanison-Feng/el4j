package com.zanthan.sequence;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.elca.el4j.util.codingsupport.annotations.FindBugsSuppressWarnings;

/**
 *
 * This class is a fasade for the Sequence library.
 * It is located in the pacakge com.zanathan.sequence to access classes that
 * have other than public visability.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author David Stefan (DST)
 */
public class Fasade {

	/**
	 * Create a Sequence Diagram of the given graph.
	 *
	 * @param graph
	 *            The graph to create the diagram for
	 * @param filename
	 *            The file to save the graph in
	 */
	public void createSequenceDiagram(String graph, String filename) {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = size.width / 2;
		int height = size.height / 2;
		createSequenceDiagram(graph, filename, width, height);
	}
	
	/**
	 * Create a Sequence Diagram of the given graph.
	 *
	 * @param graph
	 *            The graph to create the diagram for
	 * @param filename
	 *            The file to save the graph in
	 * @param width
	 *            The width of the diagram
	 * @param height
	 *            The height of the diagram
	 */
	@FindBugsSuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE",
						justification = "It is not important if the file really was newly created")
	public void createSequenceDiagram(String graph, String filename,
		int width, int height) {
		Display disp = new Display(null);
		disp.setSize(width, height);
		disp.init(graph);
		
		BufferedImage bi = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_ARGB);
		disp.paintComponent(bi.createGraphics());
		File file = new File(filename);
		try {
			file.createNewFile();
			ImageIO.write(bi, "png", file);
		} catch (IOException ioe) {
			ioe.printStackTrace();

		}
	}
}
