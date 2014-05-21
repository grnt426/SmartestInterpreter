import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A small interpreter for The Smartest Little Computer. No error handling.
 * No automatic label resolution. Its simple, not gcc.
 *
 * Author:      Grant Kurtz
 */
public class Interpret{

	HashMap<String, Integer> instructions = new HashMap<>();
	HashMap<String, Integer> registers = new HashMap<>();

	public static void main(String[] args){
		if(args.length != 1){
			System.err.println("Usage: java Interpret file.smrt");
			System.exit(1);
		}

		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(args[0]));
		}
		catch(FileNotFoundException e){
			System.err.println("File not found.");
			System.exit(1);
		}

		new Interpret(scanner);
	}

	public Interpret(Scanner scanner){

		initMap();

		int lineNum = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			String[] args = line.split(" ");
			int opc = instructions.get(args[0]);
			int shift = 6;
			for(int i = 1; i < args.length; i++){

				try{
					int imm = Integer.parseInt(args[i]);
					opc += imm;
				}
				catch(NumberFormatException nfe){
					opc += (registers.get(args[i]) << shift);
				}
				shift -= 2;
			}
			System.out.printf("%03x 1 %03x\n", lineNum++, opc);
		}
		System.out.println("0");
	}

	private void initMap(){
		instructions.put("NOP", 0x0);
		instructions.put("ADD", 0x100);
		instructions.put("ADDI", 0x200);
		instructions.put("SUB", 0x300);
		instructions.put("AND", 0x400);
		instructions.put("OR", 0x500);
		instructions.put("XOR", 0x600);
		instructions.put("SLL", 0x700);
		instructions.put("SRL", 0x800);
		instructions.put("SRA", 0x900);
		instructions.put("SB", 0xA00);
		instructions.put("LB", 0xB00);
		instructions.put("BEZ", 0xC00);
		instructions.put("BNZ", 0xD00);
		instructions.put("JUMP", 0xE00);
		instructions.put("HALT", 0xF00);

		registers.put("A", 0x0);
		registers.put("B", 0x1);
		registers.put("C", 0x2);
		registers.put("D", 0x3);
	}
}
