/**
 *
 * Created by hakan on 01/04/2017.
 */
import completions from './completions';

export const init = (ace) => {

    let langTools = ace.acequire("ace/ext/language_tools");
    let flowCompleter = {
        getCompletions: function (editor, session, pos, prefix, callback) {
            callback(null, completions.apikeys);
        }
    };
    langTools.addCompleter(flowCompleter);

    ace.define('ace/mode/docker_highlight_rules', function (require, exports, module) {
        let oop = require("ace/lib/oop");
        let JsonHighlightRules = require("ace/mode/json_highlight_rules").JsonHighlightRules;

        let DockerJsonHighlightRules = function () {
            // regexp must not have capturing parentheses. Use (?:) instead.
            // regexps are ordered -> the first match is used
            this.$rules = new JsonHighlightRules().getRules();

            this.$rules.start.unshift(
                {
                    token: "comment", // single line
                    regex: "#.*$"
                },
                {
                    token: "variable",
                    regex: " *(?:GET|POST|DELETE)[ |\t](images|containers|swarm|networks|volumes|configs|distribution|_ping|system\\/df|version|info|auth|secrets|nodes|services|tasks|plugins)(?=\\/)?"
                },
                {
                    token: "variable",
                    regex: "(filters|all|size|limit|name|rename|ps_args|term|follow|stdout|stdin|stderr|since|logs|force|ling|digest|fromImage|fromSrc|repo|tag|noprune|comment|author|pause|changes|rotateWorkerToken|rotateManagerToken|rotateManagerUnlockKey|registryAuthFrom|remote|timeout|timestamps|tail|signal|json|create|top|stats|start|stop|restart|kill)(?=\\=|\\?)?"
                }
            );
        };
        oop.inherits(DockerJsonHighlightRules, JsonHighlightRules);
        module.exports.DockerJsonHighlightRules = DockerJsonHighlightRules;
    });

    ace.define("ace/snippets/docker", ["require", "exports", "module"], function (require, exports, module) {
        // eslint-disable-next-line
        exports.snippetText = 'snippet flt\n    filters={${1:filter-name}:{${2:true}:true}}';
        exports.scope = "docker"
    });

    ace.define('ace/mode/docker', function (require, exports, module) {
        let oop = require("ace/lib/oop");
        let JSONMode = require("ace/mode/json").Mode;

        let DockerJsonHighlightRules = require("ace/mode/docker_highlight_rules").DockerJsonHighlightRules;
        let MatchingBraceOutdent = require("./matching_brace_outdent").MatchingBraceOutdent;
        let CstyleBehaviour = require("./behaviour/cstyle").CstyleBehaviour;
        let CStyleFoldMode = require("./folding/cstyle").FoldMode;
        //let WorkerClient = require("../worker/worker_client").WorkerClient;

        let Mode = function () {
            this.HighlightRules = DockerJsonHighlightRules;
            this.$outdent = new MatchingBraceOutdent();
            this.$behaviour = new CstyleBehaviour();
            this.foldingRules = new CStyleFoldMode();
        };

        oop.inherits(Mode, JSONMode);

        (function () {

            this.getCompletions = function (editor, session, pos, prefix) {
                // autocomplete is done by the autocomplete module.
                return [];
            };

            this.getNextLineIndent = function (state, line, tab) {
                let indent = this.$getIndent(line);

                if (state === "start") {
                    let match = line.match(/^.*[{([]\s*$/);
                    if (match) {
                        indent += tab;
                    }
                }

                return indent;
            };

            this.checkOutdent = function (state, line, input) {
                return this.$outdent.checkOutdent(line, input);
            };

            this.autoOutdent = function (state, doc, row) {
                this.$outdent.autoOutdent(doc, row);
            };

            this.createWorker = function (session) {
            };

            this.$id = "ace/mode/docker";

        }).call(Mode.prototype);
        exports.Mode = Mode;
    });
};
