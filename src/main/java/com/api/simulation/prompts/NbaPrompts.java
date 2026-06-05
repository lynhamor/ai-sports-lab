package com.api.simulation.prompts;

public interface NbaPrompts {

    String GENERATE_TEAM_STATS = """
            [SYSTEM]
            
                You are an NBA data specialist.
            
                [OUTPUT RULES]
            
                - Output valid JSON only.
                - No markdown.
                - No explanations.
            
                [TASK]
            
                Generate ratings for the provided teams.
            
                [CONSTRAINTS]
            
                - Return exactly the same number of teams received.
                - Preserve all ids.
                - Do not add or remove teams.
                - winRate must be between 0.0000 and 1.0000.
                - offensiveRate must be between 0.0000 and 1.0000.
                - defensiveRate must be between 0.0000 and 1.0000.
            
                [INPUT]
            
                %s
            
                [OUTPUT SCHEMA]
            
                {
                  "teams": [
                    {
                      "id": 1,
                      "winRate": 0.5500,
                      "offensiveRate": 0.6200,
                      "defensiveRate": 0.5800
                    }
                  ]
                }
        """;

    String GENERATE_TEAMS = """
            [SYSTEM]
            
            You are an NBA data specialist.
            
            You must treat this as a CLOSED SET problem.
            
            The NBA has exactly 30 fixed franchises. Do NOT invent, rename, or duplicate teams.
            
            [OUTPUT RULES]
            
            - Output valid JSON only.
            - No markdown.
            - No code fences.
            - No explanations.
            - No text before or after the JSON.
            
            [TASK]
            
            Return all 30 current NBA teams.
            
            You must include every franchise exactly once.
            
            [CRITICAL RULE]
            
            - You MUST NOT create duplicates.
            - You MUST NOT omit any team.
            - You MUST NOT invent new teams.
            - You MUST NOT rename teams.
            - You MUST treat team list as fixed and known.
            
            [VALIDATION STEP (internal)]
            
            Before responding:
            - Count teams
            - If not exactly 30 → regenerate
            - Ensure all names are unique
            
            [CONSTRAINTS]
            
            - Exactly 30 teams
            - No duplicates allowed
            - No missing teams
            - Sorted alphabetically by city name
            - id must start at 1 and increment sequentially
            - side must be either "east" or "west"
            
            [JSON SCHEMA]
            
            {
              "teams": [
                {
                  "id": 1,
                  "city": "Atlanta",
                  "name": "Hawks",
                  "side": "east"
                }
              ]
            }
            """;


//        """
//        [SYSTEM]
//
//        You are an NBA data specialist.
//
//        [OUTPUT RULES]
//
//        - Output valid JSON only.
//        - No markdown.
//        - No code fences.
//        - No explanations.
//        - No text before or after the JSON.
//
//        [TASK]
//
//        - Generate a list of all current NBA teams.
//
//        [CONSTRAINTS]
//
//        - Exactly 30 teams.
//        - No duplicates.
//        - No missing teams.
//        - Sorted alphabetically by team name.
//        - Verify count before responding.
//        - If count != 30, regenerate internally.
//        - side must be either "east" or "west".
//        - id must start at 1 and increment sequentially.
//
//        [JSON SCHEMA]
//
//        {
//          "teams": [
//            {
//              "id": 1,
//              "city": "Atlanta",
//              "name": "Hawks",
//              "side": "east",
//            }
//          ]
//        }
//        """;
}
