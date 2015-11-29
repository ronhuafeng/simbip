(defproject simbip "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot :all
  :global-vars {*warn-on-reflection* true
                *assert* false}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :java-source-paths ["src/ast"])
