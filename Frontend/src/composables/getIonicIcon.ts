import {
  bedOutline, briefcaseOutline, cafeOutline, ellipseOutline, happyOutline,
  homeOutline, peopleOutline, restaurantOutline, sparklesOutline,
  tvOutline, waterOutline, wifiOutline,
} from 'ionicons/icons';

const iconMap: Record<string, string> = {
  bedOutline,
  briefcaseOutline,
  cafeOutline,
  happyOutline,
  homeOutline,
  peopleOutline,
  restaurantOutline,
  sparklesOutline,
  tvOutline,
  waterOutline,
  wifiOutline,
};

export function getIonicIcon() {
  const getIcon = (iconName: string | undefined, fallbackLabel?: string): string => {
    if (iconName && iconMap[iconName]) {
      return iconMap[iconName];
    }
    return getFeatureIconByLabel(fallbackLabel ?? '');
  };

  const getFeatureIconByLabel = (label: string): string => {
    const f = label.toLowerCase();
    if (f.includes('wlan') || f.includes('wifi')) return wifiOutline;
    if (f.includes('person') || f.includes('gäste')) return peopleOutline;
    if (f.includes('bett') || f.includes('bed')) return bedOutline;
    if (f.includes('bad') || f.includes('dusche')) return waterOutline;
    if (f.includes('schreibtisch')) return briefcaseOutline;
    if (f.includes('minibar') || f.includes('kaffee')) return cafeOutline;
    if (f.includes('wohn') || f.includes('sitzecke')) return homeOutline;
    if (f.includes('tv') || f.includes('fernseher')) return tvOutline;
    if (f.includes('famil')) return happyOutline;
    if (f.includes('premium') || f.includes('luxus')) return sparklesOutline;
    return ellipseOutline;
  };

  return { getIcon };
}