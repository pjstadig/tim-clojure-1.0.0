/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package clojure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import clojure.lang.IMapEntry;
import clojure.lang.Keyword;
import clojure.lang.Namespace;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class mainTC{

final static private Symbol CLOJURE_MAIN = Symbol.create("clojure.main");
final static private Namespace CLOJURE_MAIN_NS = Namespace.findOrCreate(CLOJURE_MAIN);
final static private Var REQUIRE = Var.intern(RT.CLOJURE_NS, Symbol.create("require"));
final static private Var LEGACY_REPL = Var.intern(CLOJURE_MAIN_NS, Symbol.create("legacy-repl"));
final static private Var LEGACY_SCRIPT = Var.intern(CLOJURE_MAIN_NS, Symbol.create("legacy-script"));
final static private Var MAIN = Var.intern(CLOJURE_MAIN_NS, Symbol.create("main"));
final static private Keyword SHARED_KEY = Keyword.intern(null, "tcshared");
final static private Var ASSOC = Var.intern(RT.CLOJURE_NS, Symbol.create("assoc"));

public static void legacy_repl(String[] args) throws Exception{
    REQUIRE.invoke(CLOJURE_MAIN);
    LEGACY_REPL.invoke(RT.seq(args));
}

public static void legacy_script(String[] args) throws Exception{
    REQUIRE.invoke(CLOJURE_MAIN);
    LEGACY_SCRIPT.invoke(RT.seq(args));
}

public static void main(String[] args) throws Exception{
    REQUIRE.invoke(CLOJURE_MAIN);
    if (MAIN.meta().valAt(SHARED_KEY) == null) {
    	Iterator itr = CLOJURE_MAIN_NS.getMappings().iterator();
    	
    	IMapEntry me;
    	Symbol k;
    	Var v;
    	while(itr.hasNext()) {
    		me = ((IMapEntry) itr.next());
    		if (me.key() instanceof Symbol && me.val() instanceof Var) {
    			k = (Symbol) me.key();
    			v = (Var) me.val();
    			if (v.meta().valAt(SHARED_KEY) == null) {
	    			v.alterMeta(ASSOC, RT.list(SHARED_KEY, RT.T));
    			}
    		}
    	}
    }
    MAIN.applyTo(RT.seq(args));
}
}