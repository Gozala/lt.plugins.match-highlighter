(ns lt.plugins.match-highlighter
  (:require [lt.object :as object]
            [lt.objs.editor :as editor])
  (:require-macros [lt.macros :refer [behavior]]))


(behavior ::enable
          :triggers #{:object.instant}
          :type :user
          :desc "Editor: Enable match highlighter"
          :exclusive [::disable]
          :reaction (fn [this]
                      (let [value #js {:showToken (::token-pattern @this)}]
                        (editor/set-options
                         this
                         {:highlightSelectionMatches value}))))

(behavior ::disable
          :triggers #{:object.instant}
          :type :user
          :desc "Editor: Disable match highlighter"
          :exclusive [::enable]
          :reaction (fn [this]
                      (editor/set-options
                       this
                       {:highlightSelectionMatches nil})))

(behavior ::token-pattern
          :triggers #{:object.instant}
          :type :user
          :desc "Editor: Set match highlighter token pattern"
          :params [{:label "pattern"
                    :example "\\w"
                    :type :clj}]
          :reaction (fn [this pattern]
                      (object/merge! this
                                     {::token-pattern (re-pattern pattern)})))
