package polischukovik.mslibrary;

import java.math.BigInteger;

public class Dimention {
	BigInteger height, width;
	
	public Dimention(BigInteger height, BigInteger width) {
		this.height = height;
		this.width = width;
	}

	public BigInteger getWidth() {
		// TODO Auto-generated method stub
		return width;
	}


	public BigInteger getHeight() {
		// TODO Auto-generated method stub
		return height;
	}


	public void setSize(BigInteger width, BigInteger height) {
		this.width = width;
		this.height = height;		
	}

}
