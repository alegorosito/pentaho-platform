/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2008 - 2009 Pentaho Corporation.  All rights reserved.
 *
*/
package org.pentaho.platform.plugin.action.mondrian.catalog;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.plugin.services.importer.PlatformImportException;

/**
 * A service registering/enumerating registered Mondrian catalogs (schemas).
 * 
 * @author mlowery
 */
public interface IMondrianCatalogService {

  /**
   * Lists all catalogs (filtered according to access control rules).
   * @param jndiOnly return only JNDI-based catalogs
   */
  List<MondrianCatalog> listCatalogs(IPentahoSession pentahoSession, boolean jndiOnly);

  /**
   * Adds to the global catalog list and possibly persists this information.
   * @param overwrite true to overwrite existing catalog (based on match with definition and effectiveDataSourceInfo 
   */
  void addCatalog(MondrianCatalog catalog, boolean overwrite, IPentahoSession pentahoSession)
      throws MondrianCatalogServiceException;

  /**
   * Returns the catalog with the given context - name or definition allowable. Returns <code>null</code> if context not recognized. 
   * @param context Either the name of the catalog to fetch, or the catalog's definition string
   * 
   *    NOTE that the context can be the catalog name or the definition string for the catalog. If you are using the definition string to 
   *    retrieve the catalog from the cache, you cannot be guaranteed what datasource is in play; so under these circumstances, this catalog's
   *    definition is the only part of the catalog that can be trusted. As this feature was added to enable looking up Mondrian
   *    roles from the schema, we don't much care which datasource is in play. 
   */
  MondrianCatalog getCatalog(String context, final IPentahoSession pentahoSession);

  /**
   * this method loads a Mondrian schema
   * 
   * @param solutionLocation location of the schema
   * @param pentahoSession current session object
   * 
   * @return Mondrian Schema object
   */
  MondrianSchema loadMondrianSchema(String solutionLocation, IPentahoSession pentahoSession);

  /**
   * this method removes a Mondrian schema from the platform
   * 
   * @param catalogName the name of the catalog to remove
   * @param pentahoSession current session object
   */
  void removeCatalog(final String catalogName, final IPentahoSession pentahoSession);

  /**
   * Flushes the catalog cache.
   * @param pentahoSession
   */
  public void reInit(IPentahoSession pentahoSession) throws MondrianCatalogServiceException;

  /**
   * 
   * @param analysisFile
   * @param databaseConnection
   * @param parameters
   */
  public void importSchema(File analysisFile, String databaseConnection, String parameters);

  /**
   * pass in the domain overwrite and xmlaEnabled in parameters 
   * @param analysisFile
   * @param domainNameId
   * @param parameters
   * @throws PlatformImportException 
   */
  public void importSchema(InputStream analysisFile, String domainNameId, String parameters) throws PlatformImportException;
  /**
   * Extended API to support overwrite and publish XMLA enabled file
   * @param analysisFile
   * @param domainNameId
   * @param overwriteSchama
   * @param xmlaEnabled
   * @throws PlatformImportException 
   */
  public void importSchema(InputStream analysisFile, String domainNameId, boolean overwriteSchama, boolean xmlaEnabled)
      throws PlatformImportException;

  /**
   * used to remove a mondrian domain
   * @param domainNameId
   * @throws MondrianCatalogServiceException
   */
  public void removeSchema(String domainNameId) throws MondrianCatalogServiceException;
}