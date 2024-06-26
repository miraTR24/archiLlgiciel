<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE todoList SYSTEM "todoList.dtd">
<todoList>
    <simpleTask>
        <description>Faire les courses</description>
        <deadline>2024-04-23</deadline>
        <priorite>HAUTE</priorite>
        <estimatedDuration>30</estimatedDuration>
        <progress>10</progress>
    </simpleTask>
    <booleanTask>
        <description>Répondre aux e-mails</description>
        <deadline>2024-04-24</deadline>
        <priorite>MOYENNE</priorite>
        <isCompleted>false</isCompleted>
    </booleanTask>
    <complexTask>
        <description>Organiser un dîner</description>
        <deadline>2024-04-25</deadline>
        <priorite>BASSE</priorite>
        <subTasks>
            <simpleTask>
                <description>Acheter des ingrédients</description>
                <deadline>2024-04-24</deadline>
                <priorite>MOYENNE</priorite>
                <estimatedDuration>60</estimatedDuration>
                <progress>20</progress>
            </simpleTask>
            <simpleTask>
                <description>Préparer le dîner</description>
                <deadline>2024-04-25</deadline>
                <priorite>HAUTE</priorite>
                <estimatedDuration>120</estimatedDuration>
                <progress>30</progress>
            </simpleTask>
            <booleanTask>
                <description>Inviter des amis</description>
                <deadline>2024-04-26</deadline>
                <priorite>BASSE</priorite>
                <isCompleted>false</isCompleted>
            </booleanTask>
        </subTasks>
    </complexTask>
</todoList>
