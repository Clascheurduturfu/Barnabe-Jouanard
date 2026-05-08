import os

base_dir = r"c:\Users\jouan\Documents\GitHub\Barnabe-Jouanard\IPO\Zuul"

packages = {
    "pkg_commands": [
        "Command", "CommandWords", "Parser", "AleaCommand", "BackCommand", 
        "CashinCommand", "DropCommand", "GoCommand", "HelpCommand", 
        "InventoryCommand", "LookCommand", "NameCommand", "QuitCommand", 
        "TakeCommand", "TestCommand"
    ],
    "pkg_entities": [
        "Player", "Room", "TransporterRoom", "Item", "ItemList"
    ],
    "pkg_engine": [
        "GameEngine", "UserInterface", "AssetManager", "LoadingScreen"
    ]
}

def write_bluej_file(path, targets, dependencies=[]):
    with open(path, 'w', encoding='utf-8') as f:
        f.write("#BlueJ package file\n")
        # Write dependencies if any
        for i, dep in enumerate(dependencies):
            f.write(f"dependency{i+1}.from={dep['from']}\n")
            f.write(f"dependency{i+1}.to={dep['to']}\n")
            f.write(f"dependency{i+1}.type=UsesDependency\n")
        
        f.write(f"package.numDependencies={len(dependencies)}\n")
        f.write(f"package.numTargets={len(targets)}\n")
        f.write("package.showExtends=true\n")
        f.write("package.showUses=true\n")
        f.write("project.charset=UTF-8\n")
        
        for i, target in enumerate(targets):
            idx = i + 1
            f.write(f"target{idx}.height=70\n")
            f.write(f"target{idx}.name={target['name']}\n")
            if 'showInterface' in target:
                f.write(f"target{idx}.showInterface={str(target['showInterface']).lower()}\n")
            f.write(f"target{idx}.type={target['type']}\n")
            f.write(f"target{idx}.width={target.get('width', 120)}\n")
            f.write(f"target{idx}.x={target.get('x', 10 + (i%3)*150)}\n")
            f.write(f"target{idx}.y={target.get('y', 10 + (i//3)*100)}\n")

# 1. Root package.bluej
root_targets = [
    {"name": "Game", "type": "ClassTarget", "showInterface": False, "x": 100, "y": 10},
    {"name": "pkg_commands", "type": "PackageTarget", "x": 10, "y": 150},
    {"name": "pkg_entities", "type": "PackageTarget", "x": 160, "y": 150},
    {"name": "pkg_engine", "type": "PackageTarget", "x": 310, "y": 150},
    {"name": "court.txt", "type": "TextTarget", "x": 10, "y": 250},
    {"name": "testrapid.txt", "type": "TextTarget", "x": 140, "y": 250},
    {"name": "testcomplet.txt", "type": "TextTarget", "x": 270, "y": 250},
]
write_bluej_file(os.path.join(base_dir, "package.bluej"), root_targets)

# 2. Sub-packages package.bluej
for pkg, classes in packages.items():
    pkg_targets = []
    for cls in classes:
        t_type = "ClassTarget"
        if cls == "Command":
            t_type = "AbstractTarget"
        pkg_targets.append({"name": cls, "type": t_type, "showInterface": False})
    
    write_bluej_file(os.path.join(base_dir, pkg, "package.bluej"), pkg_targets)

print("BlueJ configuration generated.")
