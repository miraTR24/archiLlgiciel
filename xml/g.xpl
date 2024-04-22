<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE Taches SYSTEM "xml/Taches.dtd">

<Taches>
    <Tache type="simple">
        <Descriptif>Faire les courses</Descriptif>
        <Echeance>2024-04-30</Echeance>
        <Priorite value="normal"/>
        <Duree>1</Duree>
        <Progression>50</Progression>
    </Tache>

    <Tache type="booléenne">
        <Descriptif>Valider le budget</Descriptif>
        <Echeance>2024-05-15</Echeance>
        <Priorite value="urgent"/>
        <Duree>0</Duree>
        <!-- Pas de progression pour les tâches booléennes -->
    </Tache>

    <Tache type="complexe">
        <Descriptif>Projet de rénovation</Descriptif>
        <Echeance>2024-12-31</Echeance>
        <Priorite value="secondaire"/>
        <Duree>0</Duree> <!-- La durée est recalculée à partir des sous-tâches -->

        <SousTaches>
            <Tache type="simple">
                <Descriptif>Planification</Descriptif>
                <Echeance>2024-06-30</Echeance>
                <Priorite value="normal"/>
                <Duree>30</Duree>
                <Progression>80</Progression>
            </Tache>
            <Tache type="simple">
                <Descriptif>Construction</Descriptif>
                <Echeance>2024-12-31</Echeance>
                <Priorite value="urgent"/>
                <Duree>180</Duree>
                <Progression>30</Progression>
            </Tache>
            <Tache type="booléenne">
                <Descriptif>Valider le budget</Descriptif>
                <Echeance>2024-05-15</Echeance>
                <Priorite value="urgent"/>
                <Duree>0</Duree>
                <!-- Pas de progression pour les tâches booléennes -->
            </Tache>
        </SousTaches>
    </Tache>
</Taches>