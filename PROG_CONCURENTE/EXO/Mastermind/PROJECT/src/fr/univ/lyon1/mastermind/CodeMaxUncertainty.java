package fr.univ.lyon1.mastermind;

public class CodeMaxUncertainty {
	private final Code code;
	private final int maxUncertainty;
	
	public CodeMaxUncertainty(Code code, int maxUncertainty) {
		super();
		this.code = code;
		this.maxUncertainty = maxUncertainty;
	}

	public Code getCode() {
		return code;
	}

	public int getAmbiguity() {
		return maxUncertainty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxUncertainty;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CodeMaxUncertainty))
			return false;
		CodeMaxUncertainty other = (CodeMaxUncertainty) obj;
		if (maxUncertainty != other.maxUncertainty)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	
}
