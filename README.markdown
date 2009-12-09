Quick Start Guide
-----------------
_**REQUIREMENTS:**  You must have installed [Java](http://java.sun.com/) (duh!) and
[Maven](http://maven.apache.org/) first._

1. Install [Terracotta](http://www.terracotta.org/) (`tim-clojure-1.0.0` has been
   tested with 3.1.1) and add `$TC_INSTALL/bin` to `PATH`, where `$TC_INSTALL` is the
   directory in which you installed Terracotta.

2. If you have not already done so, set your `JAVA_HOME` environment variable to point to the directory
   where you have installed the JDK.

3. From the root of the `tim-clojure-1.0.0` project, run `mvn install`.  This will install the
   `tim-clojure-1.0.0` JAR into your local Maven repository.  The Terracotta server will automatically
   find the integration module in your Maven repository.

4. (You may want to do this next step in a separate command line window) run `start-tc-server.sh` (or
   `start-tc-server.bat` on Windows) in the directory where you checked out `tim-clojure-1.0.0`.

   This will start the Terracotta server, and it requires the `tc-config.xml` file which is contained in
   the root of the `tim-clojure-1.0.0` project.

   You may want to run this step in a separate command line window, because Terracotta will print output
   in the window where you run `start-tc-server.{sh,bat}`, and you would have to hit Ctrl-C to kill it so
   you can run the next commands.  Oops! :)

5. (This step is optional) run `dev-console.sh` (or `dev-console.bat` on Windows) to open the Terracotta
   Developer Console.  It is not necessary, but it will allow you to see what is happening in the shared
   object cache.  Kinda cool!

   Note: just like above `dev-console.{sh,bat}` will not exit, so you must either run it in a separate
   Window, or run it in the background.  You know how to do it...I don't know why I'm patronizing you. :)

6. Finally, to run a REPL execute the following command
   `dso-java.sh -cp "$HOME/.m2/repository/org/clojure/clojure/1.0.0/clojure-1.0.0.jar" clojure.main` (or
   `dso-java.bat -cp "%USERPROFILE%\.m2\repository\org\clojure\clojure\1.0.0\clojure-1.0.0.jar" clojure.main`
   on Windows).

Vars are shared through the Terracotta cache based on their metadata.  If a Var has the `:tcshared` key set
to true, then it will be shared in the Terracotta shared object cache.  If its `:tcshared` key is changed to
false, then it will be removed from the Terracotta shared object cache.

For example, if you define `user/foo` like so:

    user=> (defn #^{:tcshared true} foo [] 42)
    #'user/foo

Then `user/foo` will be available to all VMs connected to the same Terracotta server. If you then evaluate:

    user=> (alter-meta! #'foo assoc :tcshared false)
    {:ns #<Namespace user>, :name foo, :file "NO_SOURCE_PATH", :line 1, :arglists ([]), :tcshared false}

`user/foo` will be removed from the Terracotta shared object cache.

In addition to the metadata, Terracotta must be configured to "instrument" any classes that you would like
to share in the shared object cache.  This includes any functions that are defined at the REPL, or in any
source files that you `require` or `load`.  You can configure which classes Terracotta instruments in the
`tc-config.xml` file.

For example, if you wanted to share vars in the `foo.bar` namespace, then you would ammend `tc-config.xml`
like so and restart the client REPL:

    <application>
      <dso>
        <instrumented-classes>
          <include>
            <class-expression>user..*</class-expression>
          </include>
          <include>
            <class-expression>foo.bar..*</class-expression>
          </include>
        </instrumented-classes>
      </dso>
    </application>

Using the basic REPL works, and it's pretty cool!  But there are still some edge cases, and some details to
work out, so stay tuned!

What is left to do?
-------------------
More testing!  The `clojure.contrib.test-clojure` test suite runs with only a couple of minor failures, but
it is not a comprehensive test suite.


Contributing
------------
Talk to me first to make sure you are not duplicating work.  You can find me as `pjstadig` in `#clojure` on
`irc.freenode.net`, or shoot me an e-mail at [paul@stadig.name](mailto:paul@stadig.name).

I like using Git and GitHub, so I'll probably prefer a pull request.  That means it is easier to fork the code
[http://github.com/pjstadig/tim-clojure-1.0.0](http://github.com/pjstadig/tim-clojure-1.0.0) on GitHub,
and work in your local clone.

Who is to blame for this?
-------------------------
Rich isn't to blame.  He created the awesome Clojure language.  Clojure code is copyright Rich Hickey.

    Copyright (c) Rich Hickey. All rights reserved.
    The use and distribution terms for this software are covered by the
    Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
    which can be found in the file epl-v10.html at the root of this distribution.
    By using this software in any fashion, you are agreeing to be bound by
    the terms of this license.
    You must not remove this notice, or any other, from this software.

    Modifications are copyright Stadig Technologies, LLC, and released under the same license.

Terracotta Integration Module code is copyright Stadig Technologies, LLC.

    Copyright (c) Stadig Technologies, LLC. All rights reserved.
    The use and distribution terms for this software are covered by the
    Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
    which can be found in the file epl-v10.html at the root of this distribution.
    By using this software in any fashion, you are agreeing to be bound by
    the terms of this license.
    You must not remove this notice, or any other, from this software.

To keep track of progress, keep an eye on my blog [http://paul.stadig.name/](http://paul.stadig.name/).