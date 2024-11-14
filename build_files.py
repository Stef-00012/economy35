import os
import shutil


# copy assets from the src folder to the build folder
root = "src/main/java/org/cup"
assets = "src/main/java/org/cup/assets"
exe_dir = "build/executable/main"

def try_to_copy(src: str, dest: str):
    try:
        shutil.copytree(src, dest)
    except:
        pass

try_to_copy(os.path.join(assets, "UI", "fonts"), os.path.join(exe_dir, "UI", "fonts"))
try_to_copy(os.path.join(assets, "audio"), os.path.join(exe_dir, "audio"))
try_to_copy(os.path.join(assets, "sprites"), os.path.join(exe_dir, "sprites"))


try:
    shutil.copy(os.path.join(root, "logo.png"), os.path.join(exe_dir, "logo.png"))
except:
    pass