se alterar variaveis por na shared region

- [x] announceNewGame -> referee site -> incrementa # de jogo jogado
- [x] callTrial
- [x] startTrial
- [x] assertTrialDecision 
- [x] declareGameWinner -> No referee porque não vai alterar variáveis só vai dizer quem ganhou
- [x] declareMatchWinner -> No referee porque não vai alterar variáveis só vai dizer quem ganhou ao generalRepo ou então no generalRepo para fazer log, mas será necessário crair metodos para "matar" os threads


- [x] callContestants
- [x] informReferee
- [x] reviewNotes -> no inicio ter uma estratergia default, a partir daí ter maneira de escolher -> será dentro do coach

- [x] followCoachAdvice
- [x] getReady -> general repo info -> informa se está na equipa no playground
- [x] pullTheRope -> dentro do proprio contestant
- [x] amDone
- [x] seatDown -> bench -> vai buscar um customer 


# Blocking states
- **Legenda:**
    - estado bloquenate -> evento -> onde fica o evento

- **referee**
    - blocking states
        - teams Ready -> informReferee -> refereeSite
        - waitForTrialConclusion -> amDone -> playground


- **coach**
    - waitFor referee command -> callTrial -> playground
    - assemble Team -> followCoachAdvise -> bench
    - watch trial -> assertTrialDecision -> playground

- **contestant**
    - seat At the bench -> callContestants -> bench
    - stand in position -> start Trial -> playground
    - do your best -> assertTrialDecision -> playground

    -> pullTheRope (sleep : é um independent state que se bloqueia a si próprio)-> doYourBest -> amDone -> doYourBest -> assertTrialDecision -> playground