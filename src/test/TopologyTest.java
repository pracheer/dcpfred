package test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import branch.server.Topology;

import junit.framework.TestCase;

public class TopologyTest extends TestCase {
	private File tempFile_;
	
	protected void setUp() throws Exception {
		tempFile_ = File.createTempFile("topology", ".txt");
		
		String str = "";
		str += "G01 S01\n";
		str += "S01 G01\n";
		str += "S01 S05\n";
		str += "S02 S04\n";
		str += "S02 S01\n";
		str += "S02 S03\n";
		str += "err err (topology file should ignore)\n";
		str += "S02 G02\n";
		str += "G02 S02\n";
		str += "S03 S01\n";
		
		try {
			FileWriter fw = new FileWriter(tempFile_);
			fw.write(str.toCharArray());
			fw.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	protected void tearDown() {
		tempFile_.delete();
	}
	
	protected Topology createTopologyFile(String filePath, String node, String group) {
		Topology tpl = null;
		try {
			tpl = new Topology(tempFile_.getAbsolutePath(), node);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		return tpl;
	}

	public void testIsReachable() {
		/**
		 * G01 S01
		 * S01 G01
		 * S01 S05
		 * S02 S04
		 * S02 S01
		 * S02 S03
		 * S02 G02
		 * G02 S02
		 * S03 S01
		 */

		Topology tpl = createTopologyFile(tempFile_.getAbsolutePath(), "S01_1", "S01");
		assertTrue(tpl.isReachable("G01"));
		assertTrue(tpl.isReachable("S05"));
		assertFalse(tpl.isReachable("S01"));

		tpl = createTopologyFile(tempFile_.getAbsolutePath(), "G01_1", "G01");
		assertTrue(tpl.isReachable("S01"));
		assertFalse(tpl.isReachable("S02"));
		
		tpl = createTopologyFile(tempFile_.getAbsolutePath(), "S02_1", "S02");		
		assertTrue(tpl.isReachable("S04"));
		assertTrue(tpl.isReachable("S01"));
		assertTrue(tpl.isReachable("S03"));
		assertTrue(tpl.isReachable("G02"));
		
		tpl = createTopologyFile(tempFile_.getAbsolutePath(), "G02_1", "G02");
		assertTrue(tpl.isReachable("S02"));
		assertFalse(tpl.isReachable("S01"));
	}
	
	public void testTopologyCreation() {
		Topology tpl = null;
		try {
			tpl = new Topology("no-file", null);
			fail("Creating topology from bad file should raise exception.");
		} catch(IOException e) {
			assertEquals(
					"no-file (The system cannot find the file specified)",
					e.getMessage());
		}
	}
	
	public void testWhoNeighbors() {
		/**
		 * G01 S01
		 * S01 G01
		 * S01 S05
		 * S02 S04
		 * S02 S01
		 * S02 S03
		 * S02 G02
		 * G02 S02
		 */
		
		Topology tpl = null;
		
		ArrayList<String> expectedNeighbors,neighbors;
		expectedNeighbors = new ArrayList<String>();

		tpl = createTopologyFile(tempFile_.getAbsolutePath(), "S02_1", "S02");
		expectedNeighbors.clear();
		expectedNeighbors.add("S04");
		expectedNeighbors.add("S01");
		expectedNeighbors.add("S03");
		neighbors = tpl.getOutNeighbors();
		assertTrue(expectedNeighbors.equals(neighbors));
		
		tpl = createTopologyFile(tempFile_.getAbsolutePath(), "S01_1", "S01");
		expectedNeighbors.clear();
		expectedNeighbors.add("S02");
		expectedNeighbors.add("S03");
		neighbors = tpl.getInNeighbors();
		assertTrue(expectedNeighbors.equals(neighbors));
	}
}
