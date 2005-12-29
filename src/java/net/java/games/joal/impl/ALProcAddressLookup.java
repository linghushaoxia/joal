/**
 * Copyright (c) 2003-2005 Sun Microsystems, Inc. All  Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS
 * LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A
 * RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT
 * OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for use in the
 * design, construction, operation or maintenance of any nuclear facility.
 */

package net.java.games.joal.impl;

import net.java.games.joal.*;
import com.sun.gluegen.runtime.*;

/** Helper class for managing OpenAL-related proc address tables. */

public class ALProcAddressLookup {
  private static final ALProcAddressTable  alTable  = new ALProcAddressTable();
  private static volatile boolean          alTableInitialized = false;
  private static final ALCProcAddressTable alcTable = new ALCProcAddressTable();
  private static volatile boolean          alcTableInitialized = false;
  
  public static void resetALProcAddressTable() {
    if (!alTableInitialized) {
      synchronized (ALProcAddressLookup.class) {
        if (!alTableInitialized) {
          // At some point this may require an OpenAL context to be
          // current as we will actually use alGetProcAddress. Since
          // this routine is currently broken and there are no
          // per-context function pointers anyway we could actually do
          // this work anywhere. We should also in theory have
          // per-ALcontext ALProcAddressTables and per-ALCdevice
          // ALCProcAddressTables.
          ALImpl impl = (ALImpl) ALFactory.getAL();
          ProcAddressHelper.resetProcAddressTable(alTable, impl);
          alTableInitialized = true;
        }
      }
    }
  }

  public static void resetALCProcAddressTable() {
    if (!alcTableInitialized) {
      synchronized (ALProcAddressLookup.class) {
        if (!alcTableInitialized) {
          // At some point this may require an OpenAL device to be
          // created as we will actually use alcGetProcAddress. Since
          // this routine is currently broken and there are no
          // per-device function pointers anyway we could actually do
          // this work anywhere. We should also in theory have
          // per-ALcontext ALProcAddressTables and per-ALCdevice
          // ALCProcAddressTables.
          ALImpl impl = (ALImpl) ALFactory.getAL();
          ProcAddressHelper.resetProcAddressTable(alcTable, impl);
          alcTableInitialized = true;
        }
      }
    }
  }

  public static ALProcAddressTable getALProcAddressTable() {
    return alTable;
  }

  public static ALCProcAddressTable getALCProcAddressTable() {
    return alcTable;
  }
}
