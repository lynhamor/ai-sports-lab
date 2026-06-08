package com.api.simulation.prompts;

public interface NbaPrompts {

    String GENERATE_TEAM_STATS = """
            [SYSTEM]
            
                You are a deterministic NBA rating generator.
            
                You MUST follow instructions exactly. Do not interpret creatively.
            
                [OUTPUT FORMAT]
                Return ONLY valid JSON.
                No markdown.
                No explanations.
                No extra keys.
            
                [INPUT]
                You will receive a JSON array of teams.
            
                [TASK]
                For each team, generate performance ratings.
            
                Each team must be processed independently but consistently.
            
                [HARD CONSTRAINTS]
                - Output MUST contain exactly the same teams in the same order
                - Do NOT add, remove, rename, or reorder teams
                - Preserve all "id" fields exactly as provided
            
                - winRate must be a float between 0.6000 and 0.8500
                - offensiveRate must be a float between 0.6000 and 0.9000
                - defensiveRate must be a float between 0.3000 and 0.8500
            
                - All values must be rounded to 4 decimal places
            
                [BEHAVIOR RULES]
                - Treat ratings as simulated statistical outputs
                - Stronger teams should generally have higher winRate and offensiveRate
                - defensiveRate is independent but should still be realistic (avoid random extremes)
                - Do NOT output identical values for all teams
            
                [OUTPUT SCHEMA]
                {
                  "teams": [
                    {
                      "id": 1,
                      "winRate": 0.0000,
                      "offensiveRate": 0.0000,
                      "defensiveRate": 0.0000
                    }
                  ]
                }
            
                [INPUT DATA]
                %s
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
            - conference side must be either "east" or "west"
            
            [JSON SCHEMA]
            
            {
              "teams": [
                {
                  "id": 1,
                  "city": "Atlanta",
                  "name": "Hawks",
                  "conferenceSide": "east"
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
