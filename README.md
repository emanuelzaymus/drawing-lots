# drawing-lots

Small script for creating random groups from a list of people.

### What's possible

People in input member list are read from CSV file.

People needs to be divided into three groups:

- Leaders
- Active members
- Inactive members

Each leader and member has defined their **name/s, surname and points** (weight). E.g. we
assign each member one point, but when we have two member in one
entry, we assign two points.

Example

**[list-of-members.csv](https://github.com/emanuelzaymus/drawing-lots/blob/main/list-of-members.csv)**:

```
Leaders,,
JULIE,JAMES,1
JEANETTE,COOK,1
CHARLOTTE,HERRERA,1
CLYDE,HANSON,1
MARVIN,DIAZ,1
Active,,
Erik,Poole,1
Simon,White,1
Raymond,Vargas,1
Wilson,Underwood,1
Byron,Shaw,1
Earl,Wilson,1
Julie,Lee,1
Melissa,Simmons,1
Roger,Moore,1
Sarah,Russell,1
Catherine,Kelly,1
Bonnie,Flores,1
John and Ann,Coleman,2
Adam and Rebecca,Williams,2
Inactive,,
marion,snyder,1
jan,barnett,1
randolph,henry,1
robyn,thomas,1
justin and jesse,baker,2
```

There will be generated **as many groups**, as many leaders we provided in input file.

Algorithm randomly chooses members so that, there are **equally many active and inactive
member in each group** considering member's points (weight).

### How to use it

If we want to randomly create group with people in `list-of-members.csv` file
with initial seed `1000` and we want to `print` in to the console, we use:

```
% java -jar drawing-lots.jar list-of-members.csv --seed 1000 --print
╔═════════════════════════════════╤════════════════════════════════╤════════════════════════╤════════════════════════════╤════════════════════════╗
║ Group 1 (members: 6)            │ Group 2 (members: 6)           │ Group 3 (members: 5)   │ Group 4 (members: 5)       │ Group 5 (members: 5)   ║
╠═════════════════════════════════╪════════════════════════════════╪════════════════════════╪════════════════════════════╪════════════════════════╣
║ ╔══════════════════╤══════════╗ │ ╔══════════════════╤═════════╗ │ ╔══════════╤═════════╗ │ ╔══════════════╤═════════╗ │ ╔════════╤═══════════╗ ║
║ ║ MARVIN           │ DIAZ     ║ │ ║ CHARLOTTE        │ HERRERA ║ │ ║ JULIE    │ JAMES   ║ │ ║ JEANETTE     │ COOK    ║ │ ║ CLYDE  │ HANSON    ║ ║
║ ╠══════════════════╪══════════╣ │ ╠══════════════════╪═════════╣ │ ╠══════════╪═════════╣ │ ╠══════════════╪═════════╣ │ ╠════════╪═══════════╣ ║
║ ║ Adam and Rebecca │ Williams ║ │ ║ Bonnie           │ Flores  ║ │ ║ Erik     │ Poole   ║ │ ║ Raymond      │ Vargas  ║ │ ║ Wilson │ Underwood ║ ║
║ ╟──────────────────┼──────────╢ │ ╟──────────────────┼─────────╢ │ ╟──────────┼─────────╢ │ ╟──────────────┼─────────╢ │ ╟────────┼───────────╢ ║
║ ║ Julie            │ Lee      ║ │ ║ Catherine        │ Kelly   ║ │ ║ Melissa  │ Simmons ║ │ ║ John and Ann │ Coleman ║ │ ║ Roger  │ Moore     ║ ║
║ ╟──────────────────┼──────────╢ │ ╟──────────────────┼─────────╢ │ ╟──────────┼─────────╢ │ ╟──────────────┼─────────╢ │ ╟────────┼───────────╢ ║
║ ║ Sarah            │ Russell  ║ │ ║ Earl             │ Wilson  ║ │ ║ Byron    │ Shaw    ║ │ ║ marion       │ snyder  ║ │ ║ Simon  │ White     ║ ║
║ ╟──────────────────┼──────────╢ │ ╟──────────────────┼─────────╢ │ ╟──────────┼─────────╢ │ ╚══════════════╧═════════╝ │ ╟────────┼───────────╢ ║
║ ║ robyn            │ thomas   ║ │ ║ justin and jesse │ baker   ║ │ ║ randolph │ henry   ║ │                            │ ║ jan    │ barnett   ║ ║
║ ╚══════════════════╧══════════╝ │ ╚══════════════════╧═════════╝ │ ╚══════════╧═════════╝ │                            │ ╚════════╧═══════════╝ ║
╚═════════════════════════════════╧════════════════════════════════╧════════════════════════╧════════════════════════════╧════════════════════════╝


Process finished with exit code 0
```

Groups were also written into CSV file
**[drwan-groups.csv](https://github.com/emanuelzaymus/drawing-lots/blob/main/drawn-groups.csv)**
for us:

```
Group 1,
MARVIN,DIAZ
Adam and Rebecca,Williams
Julie,Lee
Sarah,Russell
robyn,thomas
,
Group 2,
CHARLOTTE,HERRERA
Bonnie,Flores
Catherine,Kelly
Earl,Wilson
justin and jesse,baker
,
Group 3,
JULIE,JAMES
Erik,Poole
Melissa,Simmons
Byron,Shaw
randolph,henry
,
Group 4,
JEANETTE,COOK
Raymond,Vargas
John and Ann,Coleman
marion,snyder
,
Group 5,
CLYDE,HANSON
Wilson,Underwood
Roger,Moore
Simon,White
jan,barnett
,
```

### Help

We can ask for descriptive `--help`:

```
% java -jar drawing-lots.jar --help                                    
Usage: drawing-lots-app [OPTIONS] <list-of-members> [<drawn-groups>]
                        [<delimiter>]

Options:
  -s, --seed INT  Random seed for shuffling, default: Random.nextLong()
  -p, --print     Print result to the console
  -h, --help      Show this message and exit

Arguments:
  <list-of-members>  Input CSV file - list of members, e.g.
                     'list-of-members.csv'
  <drawn-groups>     Output CSV file - drawn groups, default:
                     'drawn-groups.csv'
  <delimiter>        Delimiter for CSV files, default: ','
```

### Used technologies

- Kotlin
- CLI library: [Clikt](https://ajalt.github.io/clikt/)
- Console tables: [Flip Tables](https://github.com/JakeWharton/flip-tables)
- Unit-tested with: JUnit5
