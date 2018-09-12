package se.mtm.servicesCatalogDiffTool.validation;

import java.util.List;

public class InputArguments {
	
	private String mm2Url;
	private String mm3Url;
	private String outputPath;
	private List<String> ignoreFields;
	private String mediaNumbersPath;
	private String sitekeyMM2;
	private String sitekeyMM3;

	public String getMm2Url() {
		return mm2Url;
	}

	public void setMm2Url(String mm2Url) {
		this.mm2Url = mm2Url;
	}

	public String getMm3Url() {
		return mm3Url;
	}

	public void setMm3Url(String mm3Url) {
		this.mm3Url = mm3Url;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public List<String> getIgnoreFields() {
		return ignoreFields;
	}

	public void setIgnoreFields(List<String> ignoreFields) {
		this.ignoreFields = ignoreFields;
	}

	public String getMediaNumbersPath() {
		return mediaNumbersPath;
	}

	public void setMediaNumbersPath(String mediaNumbersPath) {
		this.mediaNumbersPath = mediaNumbersPath;
	}

	public String getSitekeyMM2() {
		return sitekeyMM2;
	}

	public void setSitekeyMM2(String sitekeyMM2) {
		this.sitekeyMM2 = sitekeyMM2;
	}

	public String getSitekeyMM3() {
		return sitekeyMM3;
	}

	public void setSitekeyMM3(String sitekeyMM3) {
		this.sitekeyMM3 = sitekeyMM3;
	}
}
