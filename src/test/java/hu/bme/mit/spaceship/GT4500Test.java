package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primaryTorpedoStoreMock;
  private TorpedoStore secondaryTorpedoStoreMock;

  @BeforeEach
  public void init() {
    this.primaryTorpedoStoreMock = mock(TorpedoStore.class);
    this.secondaryTorpedoStoreMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryTorpedoStoreMock, secondaryTorpedoStoreMock);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
  }

  // Sajat tesztesetek
  @Test
  public void fireTorpedo_Single_Success_BothStoresHaveAmmo() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_OnlyFirstStoreHasAmmo() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_OnlyFirstStoreHasAmmo_WasPrimaryFiredLast() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(2)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_OnlySecondStoreHasAmmo_WasPrimaryFiredLast() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(secondaryTorpedoStoreMock, times(2)).fire(1);
    verify(primaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_BothStoresHaveAmmo_WasPrimaryFiredLast() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure_BothStoresEmpty_WasPrimaryFiredLast() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertFalse(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_OnlySecondStoreHasAmmo() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
    verify(primaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure_BothStoresEmpty() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertFalse(result);
    verify(primaryTorpedoStoreMock, never()).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure_BothStoresEmpty() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertFalse(result);
    verify(primaryTorpedoStoreMock, never()).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_OnlyPrimaryStoreHasAmmo() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_OnlySecondaryStoreHasAmmo() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoStoreMock, never()).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

}
