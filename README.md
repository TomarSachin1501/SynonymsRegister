# SynonymsRegister
REST api for registering synonyms using MySQL db for storage.

The register stores words with their synonyms.
Every word could be a synonym to another word. For example, If "wash" is a synonym to "clean", then the api should return “clean” as result when we ask for the “wash” synonyms and vice versa.
A word may have multiple synonyms.
The register should support something we call a transitive rule. It assumes the following implementation, i.e. if "B" is a synonym to "A" and "C" a synonym to "B", then "C" should automatically, by transitive rule, also be the synonym for "A".
