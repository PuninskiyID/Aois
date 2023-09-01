package binCalculator;

class BinaryNum 
{
	String bin;
	int dec;
	
	private String binZero = "00000000";
	private String binOne = "00000001";
	
	public BinaryNum() 
	{
		bin = binZero;
		dec = 0;
	}
	
	public BinaryNum(int newDec) 
	{
		if(newDec > 127) { 
				System.out.println("BinNum is overloaded");
				bin = binZero;
				dec = 0;
		}
		else {
			bin = convertFromDecToBin(newDec);		
			dec = newDec;	
		}	
	}
	
	public BinaryNum(String newBin) 
	{
		if(newBin.length() != 8) {
			System.out.println("Wrong BinNum length");
		}
		else {
			if(newBin == "10000000")
				newBin = binZero;
			bin = newBin;
			dec = convertFromBinToDec(newBin);
		}
	}
	
	private static String convertFromDecToBin(int num) {
		Boolean minus = false;
		if(num < 0) { 
			minus = true;
			num *= -1;
		}
		
		int nextNum;
		String output = "";
		
		while(num !=0){  
            nextNum = num % 2;  
            output += nextNum;  
            num = num / 2;  
		}
		
		output = new StringBuilder(output).reverse().toString();
		
		while(output.length() < 7) 
			output = "0" + output;
		if(minus == true)
			output = "1" + output;
		else
			output = "0" + output;
		return output;
	};

	private static int convertFromBinToDec(String binNum) {
		Boolean minus = false;
		double output = 0;
		if(binNum.charAt(0) == '1')
		{
			binNum = "0" + binNum.substring(1, binNum.length());
			minus = true;
		}
		int degree = 0;
		for(int i = binNum.length() - 1; i >= 0 ; i--) {
			output += Math.pow(2,degree) * Character.getNumericValue(binNum.charAt(i));
			degree++;
		}
		if(minus == true)
			output *= -1;
		return (int)output;
	};
	
	public static BinaryNum Sum(BinaryNum firstBinNum, BinaryNum secondBinNum) {
		if(firstBinNum.bin.charAt(0) == '1')
			firstBinNum.ConvertToAdditional();
		if(secondBinNum.bin.charAt(0) == '1')
			secondBinNum.ConvertToAdditional();
		firstBinNum.bin = BinStringSum(firstBinNum.bin, secondBinNum.bin);
		if(firstBinNum.bin.charAt(0) == '1')
			firstBinNum.ConvertToAdditional();
		secondBinNum.ConvertToAdditional();
		firstBinNum.dec = convertFromBinToDec(firstBinNum.bin);
		return firstBinNum;
	};
	
	public static BinaryNum Diff(BinaryNum firstBinNum, BinaryNum secondBinNum) {
		String secondBinSave = secondBinNum.bin;
		StringBuilder sb = new StringBuilder(secondBinNum.bin);
		if(secondBinNum.bin.charAt(0) == '0')
			sb.setCharAt(0,'1');
		else
			sb.setCharAt(0,'0');
		secondBinNum.bin = sb.toString();
		if(firstBinNum.bin.charAt(0) == '1')
			firstBinNum.ConvertToAdditional();
		if(secondBinNum.bin.charAt(0) == '1')
			secondBinNum.ConvertToAdditional();
		firstBinNum.bin = BinStringSum(firstBinNum.bin, secondBinNum.bin);
		if(firstBinNum.bin.charAt(0) == '1')
			firstBinNum.ConvertToAdditional();
		secondBinNum.bin = secondBinSave;		
		firstBinNum.dec = convertFromBinToDec(firstBinNum.bin);
		return firstBinNum;
	};
	
	private static String BinStringSum(String firstStr, String secondStr) {
		boolean plusOne = false;
		StringBuilder sb = new StringBuilder(firstStr);
		for(int i = firstStr.length() - 1; i >=0; i--) {
			if((plusOne == true) && (firstStr.charAt(i) == '1' ^ secondStr.charAt(i) == '1'))
				sb.setCharAt(i, '0');
			else if((plusOne == false) && (firstStr.charAt(i) == '1' ^ secondStr.charAt(i) == '1'))
				sb.setCharAt(i, '1');
			else if((plusOne == false) && (firstStr.charAt(i) == '1' && secondStr.charAt(i) == '1')) {
				sb.setCharAt(i, '0');
				plusOne = true;
			}
			else if((plusOne == true) && (firstStr.charAt(i) == '0' && secondStr.charAt(i) == '0')) {
				sb.setCharAt(i, '1');
				plusOne = false;
			}
			else if((plusOne == true) && (firstStr.charAt(i) == '1' && secondStr.charAt(i) == '1'))
				sb.setCharAt(i, '1');
		}
		firstStr = sb.toString();
		return firstStr;
	};
	
	private void ConvertToReverse() {
		StringBuilder sb = new StringBuilder(this.bin);
		for(int i = 1; i < this.bin.length(); i++) {
			if(this.bin.charAt(i) == '1')
				sb.setCharAt(i,'0');
			else if(this.bin.charAt(i) == '0')
				sb.setCharAt(i,'1');
		}
		this.bin = sb.toString();
	};
	
	private void ConvertToAdditional() 
	{
		this.ConvertToReverse();
		this.bin = BinStringSum(this.bin, binOne);
	}
	
	public String ConvertToSizedString(int sizeOfString) 
	{
		String output = this.bin.substring(this.bin.length() - sizeOfString, this.bin.length());
		return output;
	}
	

}
