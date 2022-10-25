package party;

public enum Identity {
	leftWing("LeftWing"), Center("Center"), RightWing("RightWing");
	
	private String belonging;
	
	Identity(String belonging) {
		this.belonging=belonging;
	}
	
	public String getBelonging() {
		return this.belonging;
	}
	public String toString() {
		return this.getBelonging();
	}

}
