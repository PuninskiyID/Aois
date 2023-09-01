package binCalculator;

class ODS_3 {

	public static void main(String[] args) {
		System.out.println("Postroim tablitsu istinnosti:");
		BinaryNum variables = new BinaryNum();
		String Res = "";
		String Tran = "";
		
		for(int i = 0; i < Math.pow(2, 3); i++) 
		{
			System.out.print(variables.bin.substring(variables.bin.length() - 3));
			if(variables.bin.charAt(7) == '1' && variables.bin.charAt(6) == '1' && variables.bin.charAt(5) == '1') {
				System.out.println(" 1 1");
				Res += "1";
				Tran += "1";
				}
			else if(variables.bin.contains("11") || variables.bin.contains("101")) {
					System.out.println(" 0 1");
					Res += "0";
					Tran += "1";
				}
			else if (variables.bin.charAt(7) == '1' || variables.bin.charAt(6) == '1' || variables.bin.charAt(5) == '1') {
				Res += "1";
				Tran += "0";
				System.out.println(" 1 0");
				}
			else {
				System.out.println(" 0 0");
				Res += "0";
				Tran += "0";
			}
			variables = variables.Sum(new BinaryNum(1), variables);
		}
		
		
		System.out.println("Postroim PDNF res:");
		Truthtable th = new Truthtable();
		th.byAnswers(Res, "ABP");
		System.out.println(th.PDNF);
		th.calcTruthTable(th.PDNF);
		System.out.println(th.answers);
		System.out.println("Minimalization:");
		System.out.println(Minimalizer.PDNFminByCalc(th.PDNFTable, th.unicVariables));
		th.calcTruthTable(Minimalizer.PDNFminByCalc(th.PDNFTable, th.unicVariables));
		System.out.println(th.answers);
		System.out.println("Sokrashchenie do:");
		System.out.println("P^A^B");
		th.calcTruthTable("A^B^P");
		System.out.println(th.answers);
		System.out.println("Postroim PDNF Trant:");
		th.byAnswers(Tran, "ABP");
		System.out.println(th.PDNF);
		th.calcTruthTable(th.PDNF);
		System.out.println(th.answers);
		System.out.println("Minimalization:");
		System.out.println(Minimalizer.PDNFminByCalc(th.PDNFTable, th.unicVariables));
		th.calcTruthTable(Minimalizer.PDNFminByCalc(th.PDNFTable, th.unicVariables));
		System.out.println(th.answers);
		System.out.println("-----------------------------------------");
		System.out.println("D8421+4");
		System.out.println("Postroim tablitsu istinnosti:");
		Truthtable thy1 = new Truthtable();
		thy1.byAnswers("0000111111000000", "ABCD");
		String y1 = thy1.answers;
		Truthtable thy2 = new Truthtable();
		thy2.byAnswers("1111000011000000", "ABCD");
		String y2 = thy2.answers;
		Truthtable thy3 = new Truthtable();
		thy3.byAnswers("0011001100000000", "ABCD");
		String y3 = thy3.answers;
		Truthtable thy4 = new Truthtable();
		thy4.byAnswers("0101010101000000", "ABCD");
		String y4 = thy4.answers;
		
		BinaryNum num = new BinaryNum(0);
		
		for(int i = 0; i < thy1.answers.length(); i++) 
		{
			System.out.print(num.bin.substring(num.bin.length() - 4) + " ");
			System.out.print(y1.charAt(i) + " ");
			System.out.print(y2.charAt(i) + " ");
			System.out.print(y3.charAt(i) + " ");
			System.out.println(y4.charAt(i) + " ");
			num = BinaryNum.Sum(num, new BinaryNum(1));
		}
		System.out.println("Y1 PDNF");
		System.out.println(thy1.PDNF);
		System.out.println(thy1.answers);
		thy1.calcTruthTable(Minimalizer.PDNFminByTableCalc(thy1.PDNFTable, thy1.unicVariables));
		System.out.println("Y1 PDNFmin");
		System.out.println(thy1.expression);
		System.out.println(thy1.answers);
		
		System.out.println("Y2 PDNF");
		System.out.println(thy2.PDNF);
		System.out.println(thy2.answers);
		thy2.calcTruthTable(Minimalizer.PDNFminByCalc(thy2.PDNFTable, thy2.unicVariables));
		System.out.println("Y2 PDNFmin");
		System.out.println(thy2.expression);
		System.out.println(thy2.answers);
		
		System.out.println("Y3 PDNF");
		System.out.println(thy3.PDNF);
		System.out.println(thy3.answers);
		thy3.calcTruthTable(Minimalizer.PDNFminByCalc(thy3.PDNFTable, thy3.unicVariables));
		System.out.println("Y3 PDNFmin");
		System.out.println(thy3.expression);
		System.out.println(thy3.answers);
		
		System.out.println("Y4 PDNF");
		System.out.println(thy4.PDNF);
		System.out.println(thy4.answers);
		thy4.calcTruthTable(Minimalizer.PDNFminByCalc(thy4.PDNFTable, thy4.unicVariables));
		System.out.println("Y4 PDNFmin");
		System.out.println(thy4.expression);
		System.out.println(thy4.answers);
		
	}

}
