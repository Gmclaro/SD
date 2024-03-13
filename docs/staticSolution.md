# PLAYGROUND

##  internal data structure
- FIFO- Arrival of the contestants(for both teams)
- FIFO - Coachs when informing referre
- Variable Referee
## syncronization
- Access with mutual exclusion to the internal data structure

- Referee
    - blocking states
        - the referee waits fot informReferee from coach
        - the referee waits for the last amDone from contestans
- Coach 
    - blocking states 
        - coach starts in blocking state waiting for callTrial
        - coach waits for assertTrialDecision from Referee
        - coach waits for the last contestant to go to the playground followCoachAdvise
- Contestants
    - blocking states 
        - waits for referee to asserttrialDecision 

## operations called on it
- 



# REFF_SITE

## internal data structure
## syncronization
## operations called on it


# CONTESTANT BENCH

## internal data structure
## syncronization
## operations called on it