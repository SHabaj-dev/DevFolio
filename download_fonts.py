import urllib.request
import os

os.makedirs('app/src/main/res/font', exist_ok=True)
fonts = {
    'plus_jakarta_sans.ttf': 'https://raw.githubusercontent.com/google/fonts/main/ofl/plusjakartasans/PlusJakartaSans%5Bwght%5D.ttf',
    'geist.ttf': 'https://raw.githubusercontent.com/google/fonts/main/ofl/geist/Geist%5Bwght%5D.ttf'
}

for name, url in fonts.items():
    print(f"Downloading {name} from {url}")
    urllib.request.urlretrieve(url, f'app/src/main/res/font/{name}')
