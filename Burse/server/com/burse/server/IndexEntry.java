package com.burse.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.googlecode.objectify.annotation.Serialized;

/**
 * 
 */

public class IndexEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5034219662146870602L;
	char character;
	@Serialized Map<Character, IndexEntry> child = new TreeMap<Character, IndexEntry>();
	List<Object> avistring = new ArrayList<Object>();
	public static final SearchResult EMPTY = new SearchResult();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IndexEntry other = (IndexEntry) obj;
		if (character != other.character) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Char: ");
		builder.append(character);
		builder.append(", children: {");
		builder.append(child);
		builder.append("}");
		return builder.toString();
	}

	public Collection<IndexEntry> getEntriesForChar(char character) {
		if (character == '*') {
			return child.values();
		}
		IndexEntry indexEntry = child.get(character);
		if (indexEntry != null) {
			return Arrays.asList(indexEntry);
		}
		return null;
	}

	public static IndexEntry buildIndex(Map<String, Object> keyWords) {
		IndexEntry index = new IndexEntry();
		for (Map.Entry<String, Object> entry : keyWords.entrySet()) {
			String name = entry.getKey().toLowerCase();
			IndexEntry nextIndex = index;
			for (int i = 0; i < name.length(); i++) {
				char charAt = name.charAt(i);
				IndexEntry indexEntry = nextIndex.child.get(charAt);
				if (indexEntry == null) {
					indexEntry = new IndexEntry();
					indexEntry.character = charAt;
					nextIndex.child.put(charAt, indexEntry);
				}
				indexEntry.avistring.add(entry.getValue());
				nextIndex = indexEntry;
			}
		}
		return index;
	}

	public static SearchResult search(String expression, IndexEntry rootIndex) {
		long start = System.nanoTime();
		SearchResult result = new SearchResult();
		expression = expression.toLowerCase().trim();
		if (!expression.contains("*")) {
			return noWildCardSearch(expression, rootIndex);
		}
		Collection<IndexEntry> rootEntry = Arrays.asList(rootIndex);
		if (rootEntry == null || rootEntry.size() == 0) {
			return EMPTY;
		}

		Collection<IndexEntry> previousEntry = rootEntry;
		boolean skipStep = false;
		int i = 0;
		for (i = 0; i < expression.length(); i++) {
			if (skipStep) {
				skipStep = false;
				continue;
			}
			char charAt = expression.charAt(i);
			if (charAt == '*') {
				if (i + 1 == expression.length()) {
					if (i == 0) {
						previousEntry = rootIndex.getEntriesForChar('*');
					}
					break;
				} else {
					char nextChar = expression.charAt(i + 1);
					Collection<IndexEntry> foundEntries = new ArrayList<IndexEntry>();

					Collection<IndexEntry> entriesToProcced = previousEntry;
					while (entriesToProcced.size() > 0) {
						List<IndexEntry> nextEntries = new ArrayList<IndexEntry>();
						for (IndexEntry indexEntry : entriesToProcced) {
							Collection<IndexEntry> entriesForChar = indexEntry
									.getEntriesForChar(nextChar);
							if (entriesForChar != null) {
								foundEntries.addAll(entriesForChar);
							}
							nextEntries.addAll(indexEntry
									.getEntriesForChar('*'));

						}
						entriesToProcced = nextEntries;
					}
					previousEntry = foundEntries;
					skipStep = true;
					continue;
				}
			}

			List<IndexEntry> nextEntries = new ArrayList<IndexEntry>();
			for (IndexEntry entry : previousEntry) {
				Collection<IndexEntry> entriesForChar = entry
						.getEntriesForChar(charAt);

				if (entriesForChar != null) {
					nextEntries.addAll(entriesForChar);
				}
			}
			if (nextEntries.size() > 0) {
				previousEntry = nextEntries;
			} else {
				result.isPure = i == (expression.length() - 1)
						|| (charAt == '*');

				result.keyWordUsed = expression.substring(0, i);

				break;
			}
		}
		long endTime = System.nanoTime();
		long sresult = endTime - start;
		result.queryTime = sresult;

		if (previousEntry.size() == 1 && result.isPure) {

			IndexEntry indexEntry = previousEntry.iterator().next();
			while (indexEntry.child.size() == 1) {
				indexEntry = indexEntry.child.values().iterator().next();
				result.keyWordUsed += indexEntry.character;
			}
		}

		if (previousEntry.size() > 0) {
			Set<Object> treeSet = new TreeSet<Object>();
			for (IndexEntry entry : previousEntry) {

				treeSet.addAll(entry.avistring);
			}

			result.result = treeSet;
			return result;
		}

		return EMPTY;
	}

	private static SearchResult noWildCardSearch(String expression,
			IndexEntry rootIndex) {
		if (expression.length() == 0) {
			return EMPTY;
		}
		char charAt = expression.charAt(0);
		SearchResult result = new SearchResult();

		IndexEntry previusEntry = rootIndex.child.get(charAt);
		if (previusEntry == null) {

			return EMPTY;
		}
		int i = 1;
		for (i = 1; i < expression.length(); i++) {
			charAt = expression.charAt(i);
			IndexEntry indexEntry = previusEntry.child.get(charAt);
			if (indexEntry != null) {
				previusEntry = indexEntry;
			} else {

				break;
			}
		}

		if (i == expression.length()) {
			result.isPure = true;
			result.keyWordUsed = expression;
		} else {
			result.isPure = false;
			result.keyWordUsed = expression.substring(0, i);
		}
		if (result.keyWordUsed.length() < expression.length()) {

			IndexEntry indexEntry = previusEntry;
			while (indexEntry.child.size() == 1) {
				indexEntry = indexEntry.child.values().iterator().next();
				result.keyWordUsed += indexEntry.character;

			}
		}

		result.result = previusEntry.avistring;
		return result;
	}

	public static class SearchResult {
		private Collection<Object> result;
		private boolean isPure;
		private long queryTime;
		private String keyWordUsed;

		public Collection<Object> getResult() {
			return result;
		}

		public void setResult(Collection<Object> result) {
			this.result = result;
		}

		public boolean isPure() {
			return isPure;
		}

		public void setPure(boolean isPure) {
			this.isPure = isPure;
		}

		public long getQueryTime() {
			return queryTime;
		}

		public void setQueryTime(long queryTime) {
			this.queryTime = queryTime;
		}

		@Override
		public String toString() {

			return "Query generated in " + queryTime + " response is pure: "
					+ isPure + " result is\n: " + result;
		}

		public String getKeyWordUsed() {
			return keyWordUsed;
		}

		public void setKeyWordUsed(String keyWordUsed) {
			this.keyWordUsed = keyWordUsed;
		}

	}

}
