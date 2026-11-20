{
  "version": "2.0.0",
  "timestamp": "2026-11-19T20:59:17.418Z",
  "startPoint": {
    "x": 59.95042438271605,
    "y": 6.621928650655864,
    "locked": false,
    "headingDeg": 90
  },
  "lines": [
    {
      "id": "line-mu8iljkp-v1ldiv",
      "color": "#957AD9",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 57.97209499747803,
        "y": 48.86666606385032
      },
      "controlPoints": [],
      "heading": {
        "type": "constant",
        "reverse": true,
        "degrees": 90
      }
    }
  ],
  "shapes": [
    {
      "id": "triangle-1",
      "name": "Red Goal",
      "vertices": [
        {
          "x": 141.5,
          "y": 70
        },
        {
          "x": 141.5,
          "y": 141.5
        },
        {
          "x": 118.3,
          "y": 141.5
        },
        {
          "x": 135.5,
          "y": 118
        },
        {
          "x": 136.3,
          "y": 70.2
        }
      ],
      "color": "#dc2626",
      "fillColor": "#ff6b6b"
    },
    {
      "id": "triangle-2",
      "name": "Blue Goal",
      "vertices": [
        {
          "x": 6.2,
          "y": 116.9
        },
        {
          "x": 25,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 70
        },
        {
          "x": 6,
          "y": 70
        }
      ],
      "color": "#2563eb",
      "fillColor": "#60a5fa"
    }
  ],
  "sequence": [
    {
      "kind": "path",
      "lineId": "line-mu8iljkp-v1ldiv"
    }
  ],
  "fieldPoints": [],
  "robot": {
    "forwardVelocity": 75,
    "strafeVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rLength": 16,
    "rWidth": 16,
    "safetyMargin": 1,
    "maxAcceleration": 30,
    "maxDeceleration": 30
  },
  "activePaths": []
}