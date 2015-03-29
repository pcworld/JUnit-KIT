package final2.subtests;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

import test.Input;
import test.SystemExitStatus;
import test.runs.ExactRun;
import test.runs.NoOutputRun;
import test.runs.Run;

/**
 * Tests command 'create'.
 * 
 * @author Annika Berger
 * @author Roman Langrehr
 * 
 */
public class CreateTest extends LangtonSubtest {

	public CreateTest() {
		setAllowedSystemExitStatus(SystemExitStatus.WITH_0);
	}

	/**
	 * Asserts that northwards looking ants are inserted correctly
	 */
	@Test
	public void createNorthAnt() {
		inputFile = new String[] {
				"0000",
				"0000",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new ExactRun("ant", is("a")),
				new NoOutputRun("create B,1,1"),
				new ExactRun("ant", is("a,b")),
				new ExactRun("position b", is("1,1")),
				new ExactRun("direction b", is("N")),
				new ExactRun("field 1,1", is("b")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));

		inputFile = new String[] {
				"b000",
				"0000",
				"0a00",
				"Z000"
		};
		runs = new Run[] {
				new ExactRun("ant", is("a,b,z")),
				new NoOutputRun("create H,1,3"),
				new ExactRun("ant", is("a,b,h,z")),
				new ExactRun("position h", is("1,3")),
				new ExactRun("direction h", is("N")),
				new ExactRun("field 1,3", is("h")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));
	}

	/**
	 * Asserts that empty places, which had been used before, can be reused to create an ant.
	 */
	@Test
	public void recyclePlaceTest() {
		inputFile = new String[] {
				"0000",
				"0b00",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new NoOutputRun("escape b"),
				new NoOutputRun("create c,1,1"),
				new ExactRun("ant", is("a,c")),
				new ExactRun("field 1,1", is("c")),
				new ExactRun("direction c", is("S")),
				new ExactRun("position c", is("1,1")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));

		inputFile = new String[] {
				"0000",
				"0000",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new NoOutputRun("move 1"),
				new NoOutputRun("create b,2,1"),
				new ExactRun("ant", is("a,b")),
				new ExactRun("field 2,1", is("b")),
				new ExactRun("direction b", is("S")),
				new ExactRun("position b", is("2,1")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));
	}

	/**
	 * Asserts that the command create can reuse the name of ants that have left the playing field.
	 */
	@Test
	public void recycleAntNameTest() {
		inputFile = new String[] {
				"0000",
				"0b00",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new NoOutputRun("escape b"),
				new NoOutputRun("create b,0,0"),
				new ExactRun("ant", is("a,b")),
				new ExactRun("field 0,0", is("b")),
				new ExactRun("direction b", is("S")),
				new ExactRun("position b", is("0,0")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));

		inputFile = new String[] {
				"0B00",
				"0000",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new NoOutputRun("move 1"),
				new NoOutputRun("create b,0,0"),
				new ExactRun("ant", is("a,b")),
				new ExactRun("field 0,0", is("b")),
				new ExactRun("direction b", is("S")),
				new ExactRun("position b", is("0,0")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));
	}

	/**
	 * Asserts that command 'create ant,x,y' works on simple examples.
	 */
	@Test
	public void simpleCreateTest() {
		inputFile = new String[] {
				"0000",
				"0000",
				"0a00",
				"0000"
		};
		runs = new Run[] {
				new ExactRun("ant", is("a")),
				new NoOutputRun("create b,1,1"),
				new ExactRun("ant", is("a,b")),
				new ExactRun("position b", is("1,1")),
				new ExactRun("direction b", is("S")),
				new ExactRun("field 1,1", is("b")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));

		inputFile = new String[] {
				"b000",
				"0000",
				"0a00",
				"Z000"
		};
		runs = new Run[] {
				new ExactRun("ant", is("a,b,z")),
				new NoOutputRun("create h,1,3"),
				new ExactRun("ant", is("a,b,h,z")),
				new ExactRun("position h", is("1,3")),
				new ExactRun("direction h", is("S")),
				new ExactRun("field 1,3", is("h")),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile));
	}

	/**
	 * Asserts that if {@link LangtonSubtest#ALL_TYPES_BOARD} is `create`d, it still runs as expected. For detailed
	 * documentation about what's supposed to happen, see documentation of {@link LangtonSubtest#ALL_TYPES_BOARD}.
	 */
	@Test
	public void allTypesCreatedTest() {
		inputFile = new String[] {
				"0b00",
				"0041",
				"0*00",
				"0000"
		};
		runs = new Run[] {
				new NoOutputRun("create a,0,0"),
				new NoOutputRun("create J,3,2"),
				new NoOutputRun("create r,0,3"),
				new NoOutputRun("create C,3,0"),
				new NoOutputRun("create i,0,2"),
				new NoOutputRun("create D,3,1"),
				new NoOutputRun("create S,3,3"),
				checkPitch(pitchToLowercase(ALL_TYPES_BOARD)),
				move(1),
				checkPitch(ALL_TYPES_PITCHES[0]),
				move(1),
				checkPitch(ALL_TYPES_PITCHES[1]),
				move(1),
				checkPitch(ALL_TYPES_PITCHES[2]),
				move(1),
				checkPitch(ALL_TYPES_PITCHES[3]),
				move(1),
				checkPitch(ALL_TYPES_PITCHES[4]),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile), ALL_TYPES_RULE, ALL_TYPES_SPEEDUP);
	}

	/**
	 * Asserts that each lazy ant has its own movement counter, i.e. a lazy ant created between other lazy ants' moves
	 * must move in a different rhythm than the other ants.
	 */
	@Test
	public void testCreatedLazyAntMovement() {
		inputFile = new String[] {
				"y000",
				"0000",
				"0000",
				"0000"
		};
		runs = new Run[] {
				checkPitch(inputFile),
				move(1),
				checkPitch(new String[] {
						"0000",
						"y000",
						"0000",
						"0000"
				}),
				new NoOutputRun("create z,2,2"),
				checkPitch(new String[] {
						"0000",
						"y000",
						"00z0",
						"0000"

				}),
				move(1),
				checkPitch(new String[] {
						"0000",
						"y000",
						"0000",
						"00z0"
				}),
				move(1),
				checkPitch(new String[] {
						"0000",
						"3y00",
						"0000",
						"00z0"
				}),
				move(1),
				checkPitch(new String[] {
						"0000",
						"3y00",
						"0000",
						"003z"
				}),
				quit()
		};
		sessionTest(runs, Input.getFile(inputFile), "rule=270-270-270-270-270", "speedup=2");
	}

}
