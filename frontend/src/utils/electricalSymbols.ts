import { dia, shapes, util } from '@joint/core'

const { standard } = shapes

export interface EquipmentData {
  id: string
  type: string
  name: string
  status?: string
  load?: number
  capacity?: number
  current?: number
  closed?: boolean
  voltage?: number
}

export const createBusBar = (x: number, y: number, width: number, name: string, voltage?: number): dia.Element => {
  return new standard.Rectangle({
    position: { x, y },
    size: { width, height: 24 },
    attrs: {
      body: {
        fill: '#006080',
        stroke: '#00f0ff',
        strokeWidth: 2
      },
      label: {
        text: name,
        fill: '#00f0ff',
        fontSize: 14,
        fontWeight: 'bold',
        textAnchor: 'middle',
        textVerticalAnchor: 'middle',
        refX: '50%',
        refY: '50%'
      }
    },
    data: {
      type: 'busbar',
      name,
      voltage
    } as EquipmentData
  })
}

export const createTransformer = (x: number, y: number, name: string, load: number, capacity: number = 50): dia.Element => {
  const loadColor = load >= 90 ? '#ff4040' : load >= 80 ? '#ff8c00' : load >= 60 ? '#ffff00' : '#00ff80'
  
  return new standard.Ellipse({
    position: { x: x - 35, y: y - 35 },
    size: { width: 70, height: 70 },
    attrs: {
      body: {
        fill: '#804000',
        stroke: '#ffcc00',
        strokeWidth: 3
      },
      label: {
        text: name,
        fill: '#ffcc00',
        fontSize: 12,
        fontWeight: 'bold',
        textAnchor: 'middle',
        textVerticalAnchor: 'top',
        refX: '50%',
        refY: -30
      }
    },
    ports: {
      groups: {
        top: { position: 'top' },
        bottom: { position: 'bottom' }
      },
      items: [
        { id: 'top', group: 'top' },
        { id: 'bottom', group: 'bottom' }
      ]
    },
    data: {
      type: 'transformer',
      name,
      load,
      capacity
    } as EquipmentData
  })
}

export const createBreaker = (x: number, y: number, name: string, closed: boolean): dia.Element => {
  return new standard.Rectangle({
    position: { x: x - 18, y: y - 12 },
    size: { width: 36, height: 24 },
    attrs: {
      body: {
        fill: closed ? '#004020' : '#400020',
        stroke: closed ? '#00ff80' : '#ff4040',
        strokeWidth: 2,
        rx: 4,
        ry: 4
      },
      label: {
        text: name,
        fill: '#a0a0a0',
        fontSize: 10,
        textAnchor: 'middle',
        textVerticalAnchor: 'bottom',
        refX: '50%',
        refY: -5
      }
    },
    ports: {
      groups: {
        top: { position: 'top' },
        bottom: { position: 'bottom' }
      },
      items: [
        { id: 'top', group: 'top' },
        { id: 'bottom', group: 'bottom' }
      ]
    },
    data: {
      type: 'breaker',
      name,
      closed
    } as EquipmentData
  })
}

export const createFeeder = (x: number, y: number, name: string, status: string, current: number): dia.Element => {
  const isNormal = status === 'normal'
  
  return new standard.Rectangle({
    position: { x: x - 30, y: y - 18 },
    size: { width: 60, height: 36 },
    attrs: {
      body: {
        fill: isNormal ? 'rgba(0, 80, 40, 0.8)' : 'rgba(80, 0, 0, 0.8)',
        stroke: isNormal ? '#00ff80' : '#ff4040',
        strokeWidth: 2,
        rx: 6,
        ry: 6
      },
      label: {
        text: name,
        fill: isNormal ? '#00ff80' : '#ff4040',
        fontSize: 13,
        fontWeight: 'bold',
        textAnchor: 'middle',
        textVerticalAnchor: 'middle',
        refX: '50%',
        refY: '50%'
      }
    },
    ports: {
      groups: {
        top: { position: 'top' }
      },
      items: [
        { id: 'top', group: 'top' }
      ]
    },
    data: {
      type: 'feeder',
      name,
      status,
      current
    } as EquipmentData
  })
}

export const createCapacitor = (x: number, y: number, name: string, capacity: number): dia.Element => {
  return new standard.Rectangle({
    position: { x: x - 20, y: y - 35 },
    size: { width: 40, height: 70 },
    attrs: {
      body: {
        fill: 'rgba(60, 0, 100, 0.8)',
        stroke: '#8000ff',
        strokeWidth: 2,
        rx: 6,
        ry: 6
      },
      label: {
        text: name,
        fill: '#8000ff',
        fontSize: 11,
        textAnchor: 'middle',
        textVerticalAnchor: 'bottom',
        refX: '50%',
        refY: -10
      }
    },
    ports: {
      groups: {
        top: { position: 'top' }
      },
      items: [
        { id: 'top', group: 'top' }
      ]
    },
    data: {
      type: 'capacitor',
      name,
      capacity
    } as EquipmentData
  })
}

export const createLink = (sourceId: string, sourcePort: string, targetId: string, targetPort: string): dia.Link => {
  return new standard.Link({
    source: { id: sourceId, port: sourcePort },
    target: { id: targetId, port: targetPort },
    attrs: {
      line: {
        stroke: '#00f0ff',
        strokeWidth: 3,
        strokeLinecap: 'round'
      }
    },
    connector: { name: 'normal' },
    router: { name: 'normal' }
  })
}

export const createConnection = (sourceId: string, targetId: string): dia.Link => {
  return new standard.Link({
    source: { id: sourceId },
    target: { id: targetId },
    attrs: {
      line: {
        stroke: '#00f0ff',
        strokeWidth: 3,
        strokeLinecap: 'round'
      }
    },
    connector: { name: 'normal' },
    router: { name: 'normal' }
  })
}

export const getLoadColor = (load: number): string => {
  if (load >= 90) return '#ff4040'
  if (load >= 80) return '#ff8c00'
  if (load >= 60) return '#ffff00'
  return '#00ff80'
}

export const getLoadClass = (load: number): string => {
  if (load >= 90) return 'load-critical'
  if (load >= 80) return 'load-high'
  if (load >= 60) return 'load-medium'
  return 'load-normal'
}

export const getEquipmentTypeName = (type: string): string => {
  const typeMap: Record<string, string> = {
    'busbar': '母线',
    'transformer': '变压器',
    'breaker': '断路器',
    'feeder': '馈线',
    'capacitor': '电容器'
  }
  return typeMap[type] || type
}
