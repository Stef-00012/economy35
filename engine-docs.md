# Engine Docs
Libreria leggera basata su nnodi e componenti, realizzato per facilitare lo sviluppo di giochi complessi. Questo motore fornisce una solida base per creare giochi 2D con funzionalità come gestione delle scene, animazione degli sprite e gerarchie.

## Funzionalità

### Sistemi Core

- **Gestione Scene**: Struttura gerarchica delle scene con relazioni padre-figlio
- **Sistema di Trasformazione**: Sistema completo di trasformazione 2D con posizione, rotazione e scala
- **Gestione Risorse**: Cache e ridimensionamento efficiente delle immagini
- **Game Loop**: Ciclo di aggiornamento a tempo fisso con delta time
- **Gestione Grafica**: Coda di rendering basata su livelli con opzioni di antialiasing
- **Architettura a Componenti**: Sistema di componenti basato su nodi per gli oggetti di gioco

### Rendering

- **Tipi Multipli di Renderer**:
    - Renderer Sprite: Per immagini statiche
    - Renderer Quadrato: Per il rendering di forme semplici
    - Sistema di Animazione: Per animazioni basate su sprite
- **Gestione Livelli**: Rendering con ordine con aggiornamenti dinamici dei livelli
- **Punti Pivot**: Punti pivot configurabili per il rendering (CENTRO, ALTO, BASSO, ecc.)

### Animazione

- **Animazione Sprite**: Sistema di animazione basato su frame
- **Controlli Animazione**: Controlli su riproduzione, loop e timing
- **Animazioni Multiple**: Supporto per stati di animazione multipli per oggetto
- **Ottimizzazione Risorse**: Ridimensionamento e caching automatico delle immagini

## Per Iniziare

### Configurazione Base

```java
public class Main {
    public static void main(String[] args) {
        // Inizializza la finestra di gioco
        Game game = new Game("Il Mio Gioco", 1280, 720, "percorso/icona.png");

        // Crea e aggiungi una scena
        MainScene mainScene = new MainScene();
        game.addScene(mainScene);
    }
}

```

### Creazione di una Scena

```java
public class MyScene extends Scene {
    @Override
    public void init() {
        // Inizializza gli oggetti della scena
        GameNode player = new GameNode();
        addChild(player);
    }

    @Override
    public void onEnable() {
        // Chiamato quando la scena diventa attiva
    }
}

```

### Aggiunta di Sprite e Animazioni

```java
// Crea un renderer sprite
SpriteRenderer sprite = new SpriteRenderer("percorso/allo/sprite.png", transform, 1);
gameNode.addChild(sprite);

// Crea un animatore
Animator animator = new Animator(transform, 1);
animator.addAnimation("idle", new Animation(new String[]{"frame1.png", "frame2.png"}));
animator.play("idle");
gameNode.addChild(animator);

```

## Concetti Fondamentali

### Sistema di Trasformazione

La libreria utilizza un sistema di trasformazione gerarchico che permette relazioni padre-figlio:

- Posizione: Vettore 2D per il posizionamento degli oggetti
- Scala: Manipolazione delle dimensioni
- Rotazione: Rotazione angolare
- Relazioni padre-figlio: Le trasformazioni si propagano lungo la gerarchia

```java
Transform transform = new Transform();
transform.setPosition(new Vector(100, 100));
transform.setScale(new Vector(2, 2));
transform.setRotation(Math.PI / 4);

```

### Sistema a Nodi

Tutti gli oggetti di gioco ereditano dalla classe Node, fornendo:

- Struttura gerarchica
- Gestione del ciclo di vita (init, enable, disable, update)
- Architettura basata su componenti

### Coda di Rendering

Il motore utilizza un sistema di rendering a livelli:

- Gli oggetti sono ordinati per livello per un corretto sovrapposizionamento
- Gestione efficiente dell'ordine di rendering con ricerca binaria

## Best Practices

1. **Organizzazione delle Scene**
    - Mantenere le scene ben organizzate
    - Utilizzare la gerarchia dei nodi per il raggruppamento logico
    - Inizializzare gli oggetti nel metodo `init()` e nel costruttore
2. **Gestione delle Risorse**
    - Utilizzare il ResourceManager per il caricamento delle immagini
    - Dimensionare correttamente le immagini al caricamento
    - Liberare le risorse quando non più necessarie
3. **Utilizzo delle Trasformazioni**
    - Utilizzare il posizionamento relativo con relazioni padre-figlio
    - Aggiornare le trasformazioni attraverso i metodi della classe Transform
    - Considerare i punti pivot per una corretta rotazione e scalatura
4. **Animazione**
    - Organizzare i frame di animazione in modo coerente
    - Utilizzare temporizzazioni appropriate dei frame
    - Considerare l'utilizzo della memoria con grandi fogli sprite

## Dettagli Tecnici

### Considerazioni sulle Prestazioni

- Sistema di caching delle immagini per un uso ottimizzato della memoria
- Gestione della coda di rendering con ricerca binaria
- Aggiornamenti basati su delta time per movimenti costanti
- Double buffering per un rendering fluido

### Threading

- Il loop principale del gioco viene eseguito su un thread separato
- Gli aggiornamenti grafici utilizzano SwingUtilities.invokeLater per la sicurezza dei thread
- Il caricamento delle risorse è thread-safe con ConcurrentHashMap

## Esempio di Implementazione

```java
public class GameExample extends Scene {
    private Building building;
    private Employee employee;

    @Override
    public void init() {
        // Crea gli oggetti principali del gioco
        building = new Building();
        addChild(building);

        // Aggiungi stanze all'edificio
        building.addRoom();
        building.addRoom();

        // Crea e configura l'impiegato
        Room firstRoom = building.getFirstRoom();
        employee = new Employee(firstRoom);
        firstRoom.addChild(employee);
    }

    @Override
    public void onUpdate() {
        // Codice di aggiornamento della logica di gioco
    }
}

```