#EXPERIMENT 2
table_1 <- read.delim("clipboard")

table_2 <- read.delim("clipboard")

ggplot(data=table_1, aes(x=Number.of.centers, y=profits, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_line(aes(linetype=factor(Initial.solution.type)), size=1, ) +
   
  theme_ft_rc() + 
  labs( x="Nombre de centres", y="Beneficis", 
    title="Comparaci� solucions inicials segons el nombre de centres (1)",
    subtitle="Nombre de gas: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
    caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank())



ggplot(data=table_2, aes(x=Number.of.gas.stations, y=profits, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_line(aes(linetype=factor(Initial.solution.type)), size=1, ) +
  
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="Beneficis", 
        title="Comparaci� solucions inicials segons el nombre de gasolineres (2)",
        subtitle="Nombre de centres: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
        caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank())


ggplot(data=table_1, aes(x=Number.of.centers, y=execution.time.OF.THE.INITIAL.STATE.CREATION, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_line(aes(linetype=factor(Initial.solution.type)), size=1, ) +
  geom_point(size=2)+
  
  theme_ft_rc() + 
  labs( x="Nombre de centres", y="T execucio solucio (Segons)", 
        title="Comparaci� t execucio de les s inicials segons el nombre de centres (3)",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
        caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank())

ggplot(data=table_2, aes(x=Number.of.gas.stations, y=execution.time.OF.THE.INITIAL.STATE.CREATION, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_line(aes(linetype=factor(Initial.solution.type)), size=1, ) +
  geom_point(size=2)+
  
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="T execucio solucio (Segons)", 
        title="Comparaci� t execucio de les s inicials segons el nombre de gasolineres (4)",
        subtitle="Nombre de centres: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
        caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank())


ggplot(data=table_1, aes(y=execution.time.OF.THE.INITIAL.STATE.CREATION, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_boxplot() +
  theme_ft_rc() + 
  labs(  y="T execucio solucio (Segons)", 
        title="Comparaci� t execucio de les s inicials amb n de centres variable (de 10 a 100) (5)",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
        caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank()) + theme(axis.title.x=element_blank(),
                                             axis.text.x=element_blank(),
                                             axis.ticks.x=element_blank())


ggplot(data=table_2, aes(y=execution.time.OF.THE.INITIAL.STATE.CREATION, color=factor(Initial.solution.type), linetype=factor(Initial.solution.type)),)+
  geom_boxplot() +
  theme_ft_rc() + 
  labs(  y="T execucio solucio (Segons)", 
         title="Comparaci� t execucio de les s inicials amb n de gasolineres variable (de 10 a 100) (6)",
         subtitle="Nombre de centres: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre",
         caption="Dades: Experiment 2. First heuristic. Seed 1234. 1 truck per center.csv"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank()) + theme(axis.title.x=element_blank(),
                                              axis.text.x=element_blank(),
                                              axis.ticks.x=element_blank())







#EXPERIMENT 3

big_successor_2 <- read.delim("clipboard")

big_successor_3 <- read.delim("clipboard")

big_successor_4 <- read.delim("clipboard")

big_total <- read.delim("clipboard")


ggplot(data=big_total, aes(y=profits , color=factor(Successor.type), linetype=factor(Successor.type)),)+
  geom_boxplot() +
  theme_ft_rc() + 
  labs(  y="beneficis", 
         title="Comparaci� dels beneficis de les 3 opcions del annealing",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades: conjunt de les 3.xlsx    Nombre de centres: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre, Complex initial solution"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank()) + theme(axis.title.x=element_blank(),
                                              axis.text.x=element_blank(),
                                              axis.ticks.x=element_blank()) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)


ggplot(data=big_total, aes(y=execution.time , color=factor(Successor.type), linetype=factor(Successor.type)),)+
  geom_boxplot() +
  theme_ft_rc() + 
  labs(  y="temps execucio (segons)", 
         title="Comparaci� del temps d'execuci� de les 3 opcions del annealing",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades: conjunt de les 3.xlsx    Nombre de centres: 100; Heur�stica: 1; Seed: 1234; 1 cami� per centre, Complex initial solution"
  ) +
  scale_color_manual(values = c("#579199", "white", "#d6a69c"), ) +
  theme(legend.title=element_blank()) + theme(axis.title.x=element_blank(),
                                              axis.text.x=element_blank(),
                                              axis.ticks.x=element_blank()) + 
  scale_y_continuous(name="temps execucio (segons)", labels = scales::comma)


ggplot(data=big_successor_2, aes(y=execution.time , x=Steps),)+
  geom_point() +
  theme_ft_rc() + 
  labs(  y="temps execucio (segons)", 
         title="Comparaci� del temps d'execuci� segons el par�metre steps. Opci� annealing= 2",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades:EXPERIMENT 3 seed = 1234, centers = 10, gas stations = 100, Successor function type2     Heur�stica: 1; 1 cami� per centre; Complex initial solution"
  )  +
  theme(legend.title=element_blank()) + 
  scale_y_continuous(name="temps execucio (segons)", labels = scales::comma)


ggplot(data=big_successor_2, aes(y=profits ,x = Steps,  color=factor(stiter), linetype=factor(stiter)),)+
  geom_point(aes(linetype=factor(stiter))) +
  theme_ft_rc() + 
  labs(  y="Beneficis", 
         title="Comparaci� dels beneficis relaci� steps-stiter. Opcio annealing 2",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades:EXPERIMENT 3 seed = 1234, centers = 10, gas stations = 100, Successor function type2     Heur�stica: 1; 1 cami� per centre; Complex initial solution"
  )  +
  theme(legend.title=element_blank()) +
  scale_y_continuous(name="Beneficis", labels = scales::comma)


ggplot(data=big_successor_2, aes(y=profits , color=factor(lamb), linetype=factor(lamb)),)+
  geom_boxplot(aes(linetype=factor(lamb))) +
  theme_ft_rc() + 
  labs(  y="Beneficis", 
         title="Comparaci� dels beneficis, efecte de la lambda. Opcio annealing 2",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades:EXPERIMENT 3 seed = 1234, centers = 10, gas stations = 100, Successor function type2     Heur�stica: 1; 1 cami� per centre; Complex initial solution"
  )  +
  theme(legend.title=element_blank()) +
  scale_y_continuous(name="Beneficis", labels = scales::comma)+ theme(axis.title.x=element_blank(),
                                                                      axis.text.x=element_blank(),
                                                                      axis.ticks.x=element_blank()) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)


ggplot(data=big_successor_2, aes(y=profits , color=factor(Steps ), linetype=factor(Steps )),)+
  geom_boxplot(aes(linetype=factor(Steps ))) +
  theme_ft_rc() + 
  labs(  y="Beneficis", 
         title="Comparaci� dels beneficis, efecte dels steps. Opcio annealing 2",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades:EXPERIMENT 3 seed = 1234, centers = 10, gas stations = 100, Successor function type2     Heur�stica: 1; 1 cami� per centre; Complex initial solution"
  )  +
  theme(legend.title=element_blank()) +
  scale_y_continuous(name="Beneficis", labels = scales::comma)+ theme(axis.title.x=element_blank(),
                                                                      axis.text.x=element_blank(),
                                                                      axis.ticks.x=element_blank()) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)


ggplot(data=big_successor_2, aes(y=profits , color=factor(k ), linetype=factor(k )),)+
  geom_boxplot(aes(linetype=factor(k))) +
  theme_ft_rc() + 
  labs(  y="Beneficis", 
         title="Comparaci� dels beneficis, efecte de k. Opcio annealing 2",
         subtitle="69.000 combinacions per opci�. Steps:500 - 10.000 , Stiter:1 - steps , k:1 - 5000 , lambda: 1*10^(-9) - 10",
         caption="Dades:EXPERIMENT 3 seed = 1234, centers = 10, gas stations = 100, Successor function type2     Heur�stica: 1; 1 cami� per centre; Complex initial solution"
  )  +
  theme(legend.title=element_blank()) +
  scale_y_continuous(name="Beneficis", labels = scales::comma)+ theme(axis.title.x=element_blank(),
                                                                      axis.text.x=element_blank(),
                                                                      axis.ticks.x=element_blank()) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)


#EXPERIMENT 5

experiment_5_default_seed <- read.delim("clipboard")

experimen_5_big_one <- read.delim("clipboard")


ggplot(data=experiment_5_default_seed, aes(y=Beneficis, x=factor(camionsxCentre ), fill =factor(camionsxCentre ),),)+
  geom_bar(stat="identity",   alpha=0.5) +
  theme_ft_rc() + 
  labs( x="Nombre de centres", y="Beneficis", 
        title="Comparaci� dels beneficis segons el nombre de camions-centre",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Seed: 1234; Camions: 10. Complex initial solution",
        caption="Dades: Experiment05.csv"
        
  ) + theme(axis.title.x=element_blank(),
            axis.text.x=element_blank(),
            axis.ticks.x=element_blank()) + theme(legend.title=element_blank())


ggplot(data=experiment_5_default_seed, aes(y=Km.totals, x=factor(camionsxCentre ), fill =factor(camionsxCentre ),),)+
  geom_bar(stat="identity",   alpha=0.5) +
  theme_ft_rc() + 
  labs( x="Nombre de centres", y="Km totals recorreguts", 
        title="Comparaci� dels km totals recorreguts segons el nombre de camions-centre",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Seed: 1234; Camions: 10. Complex initial solution",
        caption="Dades: Experiment05.csv"
        
  ) + theme(axis.title.x=element_blank(),
            axis.text.x=element_blank(),
            axis.ticks.x=element_blank()) + theme(legend.title=element_blank())


ggplot(data=experimen_5_big_one, aes(y=Beneficis , x=camionsxCentre , color=factor(Seed ),),)+
  geom_line() +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="camions/centre", y="Beneficis", 
        title="Comparaci� dels beneficis segons el nombre de camions-centre i la seed",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Camions: 10. Complex initial solution",
        caption="Dades: Experiment05.csv"
  )


ggplot(data=experimen_5_big_one, aes(y=Km.totals , x=camionsxCentre , color=factor(Seed ),),)+
  geom_line() +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="camions/centre", y="Km totals recorreguts", 
        title="Comparaci� dels km totals recorreguts segons el nombre de camions-centre i la seed",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Camions: 10. Complex initial solution",
        caption="Dades: Experiment05.csv"
  )


ggplot(data=experimen_5_big_one, aes(y=executionTime , x=camionsxCentre , color=factor(Seed ),),)+
  geom_line() +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="camions/centre", y="temps execucio (segons)", 
        title="Comparaci� del temps d'execucio segons el nombre de camions-centre i la seed",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Camions: 10. Complex initial solution",
        caption="Dades: Experiment05.csv"
  )



#EXPERIMENT 6

experiment_6 <- read.delim("clipboard")


ggplot(data=experiment_6, aes(y=peticionsAteses  , x=costKm),)+
  geom_line() +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="cost/km", y="peticions ateses", 
        title="Comparaci� de les peticions ateses segons el cost per km",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Centres: 10. Complex initial solution; 1 cami� per centre",
        caption="Dades: Experiment06.csv"
  ) + theme(legend.title=element_blank())



ggplot(data=experiment_6, aes(y=Beneficis   , x=costKm, ), )+
  geom_link2(lwd=1,
             aes(colour = after_stat(y < 0))) +
  geom_point(size=3) +
  theme_ft_rc() + 
  labs( x="cost/km", y="peticions ateses", 
        title="Comparaci� dels beneficis segons el cost per km",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Centres: 10. Complex initial solution; 1 cami� per centre",
        caption="Dades: Experiment06.csv"
  ) + theme(legend.title=element_blank()) +
  scale_y_continuous(name="Beneficis", labels = scales::comma) + 
  scale_colour_manual(values = c("#46808c", "#c26153"))+ 
  theme(legend.position = "none")
                        
  )


#EXPERIMENT 7

experiment_7 <- read.delim("clipboard")

ggplot(data=experiment_7, aes(y=beneficis   , x=hores),)+
  geom_line() +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="hores limit", y="Beneficis", 
        title="Comparaci� dels beneficis segons les hores limit diaries per camio",
        subtitle="Nombre de gas: 100; Heur�stica: 1; Centres: 10. Complex initial solution; 1 cami� per centre",
        caption="Dades: Experiment07.csv"
  ) + theme(legend.title=element_blank())



#EXPERIMENT 4

experiment_4_climb_vs_annealing <- read.delim("clipboard")

experiment_4_annealing_vs_annealing <- read.delim("clipboard")

ggplot(data=experiment_4_climb_vs_annealing, aes(x=Nombre.Gasolineres, y=beneficis, color=factor(Tipus ), linetype=factor(Tipus )),)+
  geom_line(aes(linetype=factor(Tipus )), size=1, ) +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="Beneficis", 
        title="Evoluci� dels beneficis mantenint la proporci� 10-100",
        subtitle="Nombre de centres: gas/100; Heur�stica: 1; Seed: 1234; 1 cami� per centre; Complex Solution; Par�metres annealing d'experiment anterior" ,
        caption="Dades: experiment4.xlsx"
  ) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)



ggplot(data=experiment_4_climb_vs_annealing, aes(x=Nombre.Gasolineres, y=temps.execucio, color=factor(Tipus ), linetype=factor(Tipus )),)+
  geom_line(aes(linetype=factor(Tipus )), size=1, ) +
  geom_point(size=2) +
  geom_label_repel(data = subset(experiment_4_climb_vs_annealing, Tipus == "S annealing"), aes(label = temps.execucio),
                   box.padding   = 0.35, 
                   point.padding = 0.5,
                   segment.color = 'grey50') +
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="Temps d'execuci� (s)", 
        title="Evoluci� dels temps d'execuci� mantenint la proporci� 10-100",
        subtitle="Nombre de centres: gas/100; Heur�stica: 1; Seed: 1234; 1 cami� per centre; Complex Solution; Par�metres annealing d'experiment anterior" ,
        caption="Dades: experiment4.xlsx"
  ) + 
  scale_y_continuous(name="Temps d'execuci� (s)", labels = scales::comma)



ggplot(data=experiment_4_annealing_vs_annealing, aes(x=Nombre.Gasolineres, y=beneficis, color=factor(Tipus ), linetype=factor(Tipus )),)+
  geom_line(aes(linetype=factor(Tipus )), size=1, ) +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="Beneficis", 
        title="Evoluci� dels beneficis mantenint la proporci� 10-100, segons steps i stiter d'annealing",
        subtitle="Nombre de centres: gas/100; Heur�stica: 1; Seed: 1234; 1 cami� per centre; Complex Solution; Param annealing comuns:k=1,lambda= 1*10(-9)" ,
        caption="Dades: experiment4.xlsx"
  ) + 
  scale_y_continuous(name="Beneficis", labels = scales::comma)

ggplot(data=experiment_4_annealing_vs_annealing, aes(x=Nombre.Gasolineres, y=temps.execucio, color=factor(Tipus ), linetype=factor(Tipus )),)+
  geom_line(aes(linetype=factor(Tipus )), size=1, ) +
  geom_point(size=2) +
  theme_ft_rc() + 
  labs( x="Nombre de gasolineres", y="Temps d'execuci� (segons)", 
        title="Evoluci� temps d'execuci� mantenint la proporci� 10-100, segons steps i stiter d'annealing",
        subtitle="Nombre de centres: gas/100; Heur�stica: 1; Seed: 1234; 1 cami� per centre; Complex Solution; Param annealing comuns:k=1,lambda= 1*10(-9)" ,
        caption="Dades: experiment4.xlsx"
  ) + 
  scale_y_continuous(name="Temps d'execuci� (segons)", labels = scales::comma)

