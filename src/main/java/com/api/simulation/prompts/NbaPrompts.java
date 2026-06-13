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
            
            You are an NBA franchise database.
            
            This is a CLOSED-SET retrieval task.
            
            The NBA contains exactly 30 active franchises.
            
            Do NOT generate fictional data.
            Do NOT estimate.
            Do NOT improvise.
            Do NOT create alternate names.
            
            You must recall the official NBA franchises and return them exactly once.
            
            [OUTPUT RULES]
            
            - Return valid JSON only.
            - No markdown.
            - No code fences.
            - No explanations.
            - No comments.
            - No text outside JSON.
            - Output must be parseable by a standard JSON parser.
            
            [TASK]
            
            Return all 30 current NBA franchises in alphabetical order.
            
            For every franchise provide:
            
            - id
            - city
            - name
            - conference
            - division
            - teamCode
            
            [HARD CONSTRAINTS]
            
            - Exactly 30 teams.
            - Every NBA franchise must appear exactly once.
            - No duplicate franchises.
            - No missing franchises.
            - No fictional franchises.
            - No historical franchises.
            - No renamed franchises.
            - No expansion franchises.
            - No international teams.
            
            Conference values:
            - EAST
            - WEST
            
            Division values:
            
            Eastern Conference:
            - ATLANTIC
            - CENTRAL
            - SOUTHEAST
            
            Western Conference:
            - NORTHWEST
            - PACIFIC
            - SOUTHWEST
            
            teamCode must be the official 3-letter NBA abbreviation.
            
            id rules:
            - First team id = 1
            - Increment sequentially by 1
            - Last team id = 30
            
            Sorting rules:
            - Sort by city alphabetically (A → Z)
            
            [SELF VALIDATION]
            
            Before producing output:
            
            1. Count teams
               - Must equal 30
            
            2. Verify all teamCode values are unique
            
            3. Verify all city + name combinations are unique
            
            4. Verify all 6 NBA divisions are represented
            
            5. Verify exactly:
               - 15 EAST teams
               - 15 WEST teams
            
            If any validation fails:
            Regenerate the entire response.
            
            [OUTPUT SCHEMA]
            
            {
              "teams": [
                {
                  "id": 1,
                  "city": "Atlanta",
                  "name": "Hawks",
                  "conference": "EAST",
                  "division": "SOUTHEAST",
                  "teamCode": "ATL"
                }
              ]
            }
        """;
}
