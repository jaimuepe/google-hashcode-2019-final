package com.nevergoes.hashcode.program;

import java.io.IOException;

import com.nevergoes.hashcode.files.FileUtils;
import com.nevergoes.hashcode.parser.Parser;

public class AppMain {

	public static void main(String[] args) throws IOException {

		execute("a_example.in");

	}

	private static void execute(String fileName) throws IOException {

		Parser parser = new Parser(FileUtils.readFile(fileName));

		int nFiles = parser.valueAt(0, 0, int.class);
		int nTargets = parser.valueAt(0, 1, int.class);
		int nServers = parser.valueAt(0, 2, int.class);

		SourceFile[] sourceFiles = new SourceFile[nFiles];

		for (int i = 0; i < nFiles; i++) {

			int row = 1 + 2 * i;

			String file = parser.valueAt(row, 0, String.class);
			int compileTime = parser.valueAt(row, 1, int.class);
			int replicationTime = parser.valueAt(row, 2, int.class);

			int nDependencies = parser.valueAt(row + 1, 0, int.class);

			String[] dependencies = new String[nDependencies];

			for (int j = 0; j < nDependencies; j++) {
				String dep = parser.valueAt(row + 1, 1 + j, String.class);
				dependencies[j] = dep;
			}

			SourceFile sf = new SourceFile(file, compileTime, replicationTime, dependencies);

			sourceFiles[i] = sf;
		}

		TargetFile[] targetFiles = new TargetFile[nTargets];

		for (int k = 0; k < nTargets; k++) {

			String name = parser.valueAt(1 + 2 * nFiles + k, 0, String.class);
			int deadline = parser.valueAt(1 + 2 * nFiles + k, 1, int.class);
			int score = parser.valueAt(1 + 2 * nFiles + k, 2, int.class);

			TargetFile tf = new TargetFile(name, deadline, score);

			targetFiles[k] = tf;
		}

		System.out.println();
	}
}

class SourceFile {

	final String name;
	final int compilationTime;
	final int replicationTime;

	final String[] dependencies;

	public SourceFile(String name, int compilationTime, int replicationTime, String[] dependencies) {
		this.name = name;
		this.compilationTime = compilationTime;
		this.replicationTime = replicationTime;
		this.dependencies = dependencies;
	}
}

class TargetFile {

	final String name;
	final int deadline;
	final int score;

	public TargetFile(String name, int deadline, int score) {
		this.name = name;
		this.deadline = deadline;
		this.score = score;
	}

}