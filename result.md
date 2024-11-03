# Résultats des heuristiques

## Performance des heuristiques

Ci-dessous, la performance de chaque heuristique que nous avons implémenté sur chaque dataset. Les nombres affichés correspondent à la longueur de la tournée trouvé par l'heuristique.

### pcb442

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 56963 | 59646 | 58296   | 50778   | 12.18             | 513.41          |
| NearestInsertion    | 59212 | 61829 | 60413   | 50778   | 16.61             | 557.92          |
| FurthestInsertion   | 55428 | 60254 | 57401   | 50778   | 9.16              | 722.09          |

### att532

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 94481 | 98438 | 96551   | 86729   | 8.94              | 758.25          |
| NearestInsertion    | 106471| 108741| 107475  | 86729   | 22.76             | 420.04          |
| FurthestInsertion   | 91479 | 97886 | 94515   | 86729   | 5.48              | 1084.93         |

### u574

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 40665 | 42118 | 41352   | 36905   | 10.19             | 351.20          |
| NearestInsertion    | 44731 | 46160 | 45530   | 36905   | 21.21             | 304.21          |
| FurthestInsertion   | 39379 | 42020 | 40805   | 36905   | 6.70              | 460.63          |

### pcb1173

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 65132 | 67466 | 66101   | 56892   | 14.48             | 350.13          |
| NearestInsertion    | 70768 | 72816 | 72223   | 56892   | 24.39             | 441.62          |
| FurthestInsertion   | 63557 | 67153 | 65706   | 56892   | 11.72             | 496.28          |

### nrw1379

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 62727 | 64323 | 63571   | 56638   | 10.75             | 265.64          |
| NearestInsertion    | 68982 | 69778 | 69461   | 56638   | 21.79             | 144.36          |
| FurthestInsertion   | 61606 | 64191 | 62875   | 56638   | 8.77              | 396.37          |

### u1817

| Méthode             | Min   | Max   | Moyenne | Optimal | Écart relatif (%) | Écart-type      |
|---------------------|-------|-------|---------|---------|-------------------|-----------------|
| RandomTourBuilder   | 67671 | 69614 | 68646   | 57201   | 18.30             | 369.33          |
| NearestInsertion    | 70139 | 71537 | 70910   | 57201   | 22.62             | 238.50          |
| FurthestInsertion   | 67153 | 71364 | 69009   | 57201   | 17.40             | 550.54          |

## Conclusion

On peut voir que sur tout les sets de données, la méthode de construction de tour par l'heristique **« Insertion la plus éloignée »** est la plus performante. Elle a l'écart relatif le plus faible et donc ses résultats sont les plus proches de l'optimal.
Nous avons ensuite la méthode de construction de tour par l'heristique **« Insertion la plus proche »** qui est la moins performante et finalement la méthode de construction de tour **aléatoire** qui se situe juste après la méthode de l'insertion la plus éloignée.

Globalement, les résultats sont très proches de l'optimal, ce qui est très satisfaisant.

On note aussi que bien que la méthode de l'insertion la plus éloignée soit la plus performante, elle est aussi celle avec le plus grand écart-type. Aussi, toutes les méthodes ont un temps d'exécution très similaire, ce qui est très intéressant.
