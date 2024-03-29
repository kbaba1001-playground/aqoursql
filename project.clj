(defproject aqoursql "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.9.1"
  :dependencies [[com.walmartlabs/lacinia-pedestal "0.11.0"]
                 [duct.module.pedestal "2.0.1"]
                 [duct/core "0.7.0"]
                 [duct/module.logging "0.4.0"]
                 [duct/module.sql "0.5.0"]
                 [honeysql "0.9.4"]
                 [io.pedestal/pedestal.jetty "0.5.5"]
                 [io.pedestal/pedestal.service "0.5.5"]
                 [org.clojure/clojure "1.10.0"]
                 [org.mariadb.jdbc/mariadb-java-client "2.4.1"]]
  :plugins [[duct/lein-duct "0.12.1"]]
  :main ^:skip-aot aqoursql.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:shared :project/dev :profiles/dev]
   :test [:shared :project/dev :project/test :profiles/test]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar [:shared :project/ubarjar]
   :shared {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[clj-http "3.9.1"]
                                   [com.bhauman/rebel-readline "0.1.4"]
                                   [com.gearswithingears/shrubbery "0.4.1"]
                                   [eftest "0.5.7"]
                                   [integrant/repl "0.3.1" :exclusions [integrant]]
                                   [orchestra "2019.02.06-1"]
                                   [pjstadig/humane-test-output "0.9.0"]
                                   [vincit/venia "0.2.5"]]
                  :plugins [[jonase/eastwood "0.3.5"]
                            [lein-ancient "0.6.15"]
                            [lein-cljfmt "0.6.4"]
                            [lein-cloverage "1.1.1"]
                            [lein-codox "0.10.6"]
                            [lein-kibit "0.1.6"]]
                  :aliases {"rebel" ^{:doc "Run REPL with rebel-readline."}
                            ["trampoline" "run" "-m" "rebel-readline.main"]
                            "test-coverage" ^{:doc "Execute cloverage."}
                            ["cloverage" "--ns-exclude-regex" "^(:?dev|user)$" "--codecov" "--junit"]
                            "lint" ^{:doc "Execute cljfmt check, eastwood and kibit."}
                            ["do"
                             ["cljfmt" "check"]
                             ["eastwood" "{:config-files [\"dev/resources/eastwood_config.clj\"]
                                           :source-paths [\"src\"]
                                           :test-paths []}"]
                             ["kibit"]]}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  :cljfmt {:indents {fdef [[:inner 0]]
                                     for-all [[:inner 0]]}}
                  :codox {:output-path "target/codox"
                          :source-uri "https://github.com/lagenorhynque/aqoursql/blob/master/{filepath}#L{line}"
                          :metadata {:doc/format :markdown}}}
   :project/test {}
   :project/ubarjar {:aot :all :ubarjar-name "aqoursql.jar"}
   :profiles/dev {}
   :profiles/test {}})
