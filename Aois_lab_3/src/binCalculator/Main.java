package binCalculator;
import binCalculator.BinaryNum;


public class Main {  

	public static void main(String[] args)  {
		Truthtable tb = new Truthtable ();
		tb.calcTruthTable("((-A)/\\(-B)/\\(-C)/\\D)\\/((-A)/\\(-B)/\\C/\\D)\\/((-A)/\\B/\\(-C)/\\D)\\/((-A)/\\B/\\C/\\D)\\/(A/\\B/\\C/\\(-D)\\/(A/\\B/\\C/\\D))");
		System.out.println("Expreion to Minimalize :");
		System.out.println(tb.expression);
		System.out.println("Truthtable :");
		System.out.println(tb.mainTable);
		System.out.println(tb.answers);
		System.out.println("PDNF :");
		System.out.println(tb.PDNF);
		System.out.println("PKNF :");
		System.out.println(tb.PKNF);
		Truthtable tbForPDNF = new Truthtable ();
		Truthtable tbForPKNF = new Truthtable ();
		System.out.println("---------------------------------------------------");
		System.out.println("Minimalization by CalcMethod :");
		System.out.println("PDNF :");
		tbForPDNF.calcTruthTable(Minimalizer.PDNFminByCalc(tb.PDNFTable, tb.unicVariables));
		System.out.println(tbForPDNF.expression);
		System.out.println(tbForPDNF.answers);
		System.out.println("PKNF :");
		tbForPKNF.calcTruthTable(Minimalizer.PKNFminByCalc(tb.PKNFTable, tb.unicVariables));
		System.out.println(tbForPKNF.expression);
		System.out.println(tbForPKNF.answers);
		System.out.println("---------------------------------------------------");
		System.out.println("Minimalization by TableCalcMethod :");
		System.out.println("PDNF :");
		tbForPDNF.calcTruthTable(Minimalizer.PDNFminByTableCalc(tb.PDNFTable, tb.unicVariables));
		System.out.println(tbForPDNF.expression);
		System.out.println(tbForPDNF.answers);
		System.out.println("PKNF :");
		tbForPKNF.calcTruthTable(Minimalizer.PKNFminByTableCalc(tb.PKNFTable, tb.unicVariables));
		System.out.println(tbForPKNF.expression);
		System.out.println(tbForPKNF.answers);
		System.out.println("---------------------------------------------------");
		System.out.println("Minimalization by TableMethod :");
		System.out.println("PDNF :");
		tbForPDNF.calcTruthTable(Minimalizer.PDNFminByTable(tb.PDNFTable, tb.unicVariables));
		System.out.println(tbForPDNF.expression);
		System.out.println(tbForPDNF.answers);
		System.out.println("PKNF :");
		tbForPKNF.calcTruthTable(Minimalizer.PKNFminByTable(tb.PKNFTable, tb.unicVariables));
		System.out.println(tbForPKNF.expression);
		System.out.println(tbForPKNF.answers);
		
	}

}
