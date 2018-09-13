package se.mtm.servicesCatalogDiffTool.validation;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

	public static final String ARGUMENT_MM2_URL = "mm2url";
	public static final String ARGUMENT_MM3_URL = "mm3url";
	public static final String ARGUMENT_OUTPUT = "output";
	public static final String ARGUMENT_IGNORE = "ignore";
	public static final String ARGUMENT_SITEKEY_MM2 = "sitekeyMM2";
	public static final String ARGUMENT_SITEKEY_MM3 = "sitekeyMM3";
	public static final String ARGUMENT_MEDIANUMBERS_PATH = "medianumbers";

	public static final String REGEX_DATAFIELD = "(\\d{3})( |\\d|[a-z]){2}([a-z]|\\d)*";
	public static final String REGEX_CONTROLFIELD = "\\d{3}";
	public static final String REGEX_LEADERFIELD = "(leader|LEADER)";
	public static final Pattern PATTERN_DATAFIELD = Pattern.compile(REGEX_DATAFIELD);
	public static final Pattern PATTERN_CONTROLFIELD = Pattern.compile(REGEX_CONTROLFIELD);
	public static final Pattern PATTERN_LEADERFIELD = Pattern.compile(REGEX_LEADERFIELD);

	/**
	 * Validates program arguments.
	 * 
	 * @param args
	 * @return
	 */
	public InputArguments validateInput(String[] args) {
		Options options = generateOptions();
		InputArguments arguments = parseArguments(options, args);
		validateArguments(arguments);
		return arguments;
	}

	private Options generateOptions() {
		Options options = new Options();

		Option mm2Url = new Option("mm2", ARGUMENT_MM2_URL, true, "URL to MM2");
		mm2Url.setRequired(true);
		options.addOption(mm2Url);

		Option mm3Url = new Option("mm3", ARGUMENT_MM3_URL, true, "URL to MM3");
		mm3Url.setRequired(true);
		options.addOption(mm3Url);

		Option output = new Option("o", ARGUMENT_OUTPUT, true, "absolute path for output file, e.g. C:\\templ");
		output.setRequired(true);
		options.addOption(output);

		Option sitekeyMM2 = new Option("sk2", ARGUMENT_SITEKEY_MM2, true, "Sitekey for services, e.g. testkey");
		sitekeyMM2.setRequired(true);
		options.addOption(sitekeyMM2);

		Option sitekeyMM3 = new Option("sk3", ARGUMENT_SITEKEY_MM3, true, "Sitekey for services, e.g. testkey");
		sitekeyMM3.setRequired(true);
		options.addOption(sitekeyMM3);

		Option ignore = new Option("i", ARGUMENT_IGNORE, true, "marc fields to ignore");
		ignore.setRequired(false);
		ignore.setArgs(-2);
		ignore.setValueSeparator(',');
		options.addOption(ignore);

		Option mediaNumbersPath = new Option("m", ARGUMENT_MEDIANUMBERS_PATH, true, "path to text file containing media numbers separated by new line");
		mediaNumbersPath.setRequired(true);
		options.addOption(mediaNumbersPath);

		return options;
	}

	private InputArguments parseArguments(Options options, String[] args) {
		CommandLine commandLine = null;
		try  {
			commandLine = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			new HelpFormatter().printHelp("<application name>", options);
			System.exit(-1);
		}

		InputArguments arguments = new InputArguments();
		arguments.setMm2Url(commandLine.getOptionValue(ARGUMENT_MM2_URL));
		arguments.setMm3Url(commandLine.getOptionValue(ARGUMENT_MM3_URL));
		arguments.setOutputPath(commandLine.getOptionValue(ARGUMENT_OUTPUT));
		arguments.setSitekeyMM2(commandLine.getOptionValue(ARGUMENT_SITEKEY_MM2));
		arguments.setSitekeyMM3(commandLine.getOptionValue(ARGUMENT_SITEKEY_MM3));
		arguments.setMediaNumbersPath(commandLine.getOptionValue(ARGUMENT_MEDIANUMBERS_PATH));
		String[] ignoreFields = commandLine.getOptionValues(ARGUMENT_IGNORE);
		arguments.setIgnoreFields(ignoreFields != null ? Arrays.asList(ignoreFields) : Collections.emptyList());

		return arguments;
	}

	private void validateArguments(InputArguments arguments) {
		List<String> fieldsToIgnore = arguments.getIgnoreFields();
		if (fieldsToIgnore != null) {
			for (String field : fieldsToIgnore) {
				Matcher matcherDatafield = PATTERN_DATAFIELD.matcher(field);
				Matcher matcherControlfield = PATTERN_CONTROLFIELD.matcher(field);
				Matcher matcherLeaderfield = PATTERN_LEADERFIELD.matcher(field);

				if (!matcherDatafield.matches() && !matcherControlfield.matches() && !matcherLeaderfield.matches()) {
					throw new IllegalArgumentException("Wrong pattern, should be: --ignore \"245 1abc\",007,leader");
				}

			}
		}

		String mediaNumbersPath = arguments.getMediaNumbersPath();
		if (!(new File(mediaNumbersPath)).exists()) {
			throw new IllegalArgumentException("The file: " + mediaNumbersPath + " could not be found!");
		}
	}

}
