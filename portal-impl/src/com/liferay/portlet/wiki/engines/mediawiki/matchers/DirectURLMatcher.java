/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki.engines.mediawiki.matchers;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.regex.MatchResult;

/**
 * @author Kenneth Chang
 */
public class DirectURLMatcher extends CallbackMatcher {

	public DirectURLMatcher(WikiPage page, String attachmentURLPrefix) {
		_page = page;
		_attachmentURLPrefix = attachmentURLPrefix;

		setRegex(_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, false);
	}

	public String replaceMatches(
		CharSequence charSequence, boolean hasUnderscore) {

		if (hasUnderscore) {
			setRegex(_UNDERSCORE_REGEX);
		}

		return replaceMatches(charSequence, _callBack);
	}

	private static final String _REGEX =
		"<a href=\"[^\"]*?Special:Edit[^\"]*?topic=[^\"]*?\".*?title=\"" +
			"([^\"]*?)\".*?>(.*?)</a>";

	private static final String _UNDERSCORE_REGEX =
		"<p>([^|<]*)[|]*?([^|<]*)<\\/p>";

	private String _attachmentURLPrefix;

	private Callback _callBack = new Callback() {

		public String foundMatch(MatchResult matchResult) {
			String fileName = StringUtil.replace(
				matchResult.group(1), "%5F", StringPool.UNDERLINE);
			String title = StringUtil.replace(
				matchResult.group(2), "%5F", StringPool.UNDERLINE);

			if (title.isEmpty()) {
				title = fileName;
			}

			String url = _attachmentURLPrefix + HttpUtil.encodeURL(fileName);

			try {
				boolean exists = false;

				for (FileEntry fileEntry : _page.getAttachmentsFileEntries()) {
					if (fileName.equals(fileEntry.getTitle())) {
						exists = true;

						break;
					}
				}

				if (!exists) {
					return null;
				}
			}
			catch (Exception e) {
				return null;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("<a href=\"");
			sb.append(url);
			sb.append("\">");
			sb.append(title);
			sb.append("</a>");

			return sb.toString();
		}

	};

	private WikiPage _page;

}