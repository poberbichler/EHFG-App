package org.ehfg.app.web.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class BootstrapStack implements JavaScriptStack {
	private final Asset[] javascripts;
	private final StylesheetLink[] stylesheets;
	
	public BootstrapStack(final AssetSource assetSource) {
		javascripts = new Asset[] {
			assetSource.getClasspathAsset("/org/ehfg/app/web/style/bootstrap/js/bootstrap.js", null)
		};
		
		stylesheets = new StylesheetLink[] {
			new StylesheetLink(assetSource.getClasspathAsset(
					"/org/ehfg/app/web/style/bootstrap/css/bootstrap-theme.css", null)),
					
			new StylesheetLink(assetSource.getClasspathAsset(
					"/org/ehfg/app/web/style/bootstrap/css/bootstrap.css", null)),
					
			new StylesheetLink(assetSource.getClasspathAsset("/org/ehfg/app/web/style/bootstrap/layout.css", null))
		};
	}
	
	@Override
	public List<String> getStacks() {
		return Collections.emptyList();
	}

	@Override
	public List<Asset> getJavaScriptLibraries() {
		return Arrays.asList(javascripts);
	}

	@Override
	public List<StylesheetLink> getStylesheets() {
		return Arrays.asList(stylesheets);
	}

	@Override
	public String getInitialization() {
		return null;
	}

}
