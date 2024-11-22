package model;

public class UserData {
	String name;
	int totalScore;
	int totalStage;
	int countFrozen;
	int countShield;
	
	public UserData(String name, int totalScore, int totalStage, int countFrozen, int countShield) {
        this.name = name;
        this.totalScore = totalScore;
        this.totalStage = totalStage;
        this.countFrozen = countFrozen;
        this.countShield = countShield;
    }
	public String getNameData() {
		return name;
	}
	public int getScoreData() {
		return totalScore;
	}
	public int getStageData() {
		return totalStage;
	}
	public int getFrozenCount() {
		return countFrozen;
	}
	public int getShieldCount() {
		return countShield;
	}
}