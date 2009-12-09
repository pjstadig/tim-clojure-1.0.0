package com.stadigtech.terracotta.modules.tim_clojure_1_0_0;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.terracotta.modules.configuration.TerracottaConfiguratorModule;

public class ClojureTerracottaConfigurator extends TerracottaConfiguratorModule {
	@Override
	protected void addInstrumentation(BundleContext context) {
		super.addInstrumentation(context);
		Bundle bundle = getExportedBundle(context,
				"com.stadigtech.terracotta.modules.tim-clojure-1.0.0");
		addClassReplacement(bundle, "clojure.lang.RT", "clojure.lang.RTTC");
		addClassReplacement(bundle, "clojure.lang.RT$1", "clojure.lang.RTTC$1");
		addClassReplacement(bundle, "clojure.lang.RT$2", "clojure.lang.RTTC$2");
		addClassReplacement(bundle, "clojure.lang.RT$3", "clojure.lang.RTTC$3");
		addClassReplacement(bundle, "clojure.lang.RT$4", "clojure.lang.RTTC$4");
		addClassReplacement(bundle, "clojure.lang.RT$5", "clojure.lang.RTTC$5");
		addClassReplacement(bundle, "clojure.lang.RT$6", "clojure.lang.RTTC$6");
		addClassReplacement(bundle, "clojure.lang.Var", "clojure.lang.VarTC");
		addClassReplacement(bundle, "clojure.lang.Var$1", "clojure.lang.VarTC$1");
		addClassReplacement(bundle, "clojure.lang.Var$2", "clojure.lang.VarTC$2");
		addClassReplacement(bundle, "clojure.lang.DynamicClassLoader", "clojure.lang.DynamicClassLoaderTC");
		addClassReplacement(bundle, "clojure.main", "clojure.mainTC");
		addClassReplacement(bundle, "clojure.lang.Symbol", "clojure.lang.SymbolTC");
	}
}
