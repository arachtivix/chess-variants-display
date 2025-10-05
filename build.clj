(ns build
  (:require [clojure.tools.build.api :as b]
            [clojure.string :as str]))

(def lib 'chess-variants-display/chess-variants-display)

(defn- read-base-version []
  (str/trim (slurp "VERSION")))

(defn- calculate-version []
  (let [base-version (read-base-version)
        [major minor _] (str/split base-version #"\.")
        patch (b/git-count-revs nil)]
    (format "%s.%s.%s" major minor patch)))

(def version (calculate-version))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]
                :scm {:url "https://github.com/arachtivix/chess-variants-display"
                      :connection "scm:git:git://github.com/arachtivix/chess-variants-display.git"
                      :developerConnection "scm:git:ssh://git@github.com/arachtivix/chess-variants-display.git"}})
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file})
  (println "Built JAR:" jar-file))

(defn install [_]
  (jar nil)
  (b/install {:basis basis
              :lib lib
              :version version
              :jar-file jar-file
              :class-dir class-dir})
  (println "Installed to local Maven repository"))
